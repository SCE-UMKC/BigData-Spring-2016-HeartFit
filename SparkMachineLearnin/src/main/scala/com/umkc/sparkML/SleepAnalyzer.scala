package com.umkc.sparkML

import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, StringType, StructField}

/**
  * Created by Hiren Shah on 5/3/2016.
  */
object SleepAnalyzer   {
  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir", "c:\\winutils")
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf()
      .setAppName("MovieLensALS")
      .set("spark.executor.memory", "2g").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //mysql connection
    val databaseUsername = "heartfit_root"
    val databasePassword = "heart123"
    val databaseConnectionUrl = "jdbc:mysql://srv70.hosting24.com:3306/heartfit_123?user=" + databaseUsername + "&password=" + databasePassword


    val dataset = sc.textFile("ml-100k/sample_fpgrowth.txt")
      .map(line => {
        val fields = line.split(" ")
        (fields(0).toDouble, fields(1).toDouble, fields(2).toDouble, fields(3), fields(4).toInt, fields(5).toInt)
      })

    val pairs = dataset.cartesian(dataset)

    val filter = pairs.filter(f => (f._1._1 != f._2._1 || f._1._2 != f._2._2 || f._1._3 != f._2._3) && f._1._4 == f._2._4 && f._1._5 == f._2._5 && f._1._6 == f._2._6)

    //filter.collect().foreach(println)

    val result = filter.map(r => {
      val key = (r._1._1,r._1._2,r._1._3,r._1._4,r._1._5,r._1._6)
      val value = 1;

      (key, value)
    }).reduceByKey(_ + _)

   // result.collect().foreach(println)


    val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    import sqlContext.implicits._


   val df =  result.map {
     case (k, v) => acc(k._1, k._2, k._3, k._4, k._5, k._6,v) }.toDF()


    val accl = Window.partitionBy('d,'t,'h,'cnt).orderBy('d.desc)

    val ranked = df.withColumn("rank", row_number().over(accl))
    ranked.registerTempTable("resultitems")

    val finalresults = sqlContext.sql("SELECT x,y,z,d,t,h,cnt FROM resultitems where rank = 1")

    //finalresults.collect().foreach(println)

    finalresults.insertIntoJDBC(databaseConnectionUrl,"output_accelerometer",false)
  }

  case class acc(x: Double,y: Double, z: Double, d: String, t: Int, h: Int, cnt: Int)

  case class StructType(value: Any)

}

