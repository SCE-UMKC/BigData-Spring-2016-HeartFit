package com.umkc.sparkML

import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
  * Created by hhstm4 on 5/3/2016.
  */
object SleepAnalyzer {
  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir", "c:\\winutils")
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf()
      .setAppName("MovieLensALS")
      .set("spark.executor.memory", "2g").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val dataset = sc.textFile("ml-100k/sample_fpgrowth.txt")
      .map(line => {
        val fields = line.split(" ")
        (fields(0).toInt, fields(1).toInt, fields(2).toInt, fields(3).toInt)
      })

    val count = dataset.count()

    var i = 0
    for(i <- 1 to count.toInt){
      
        println(i)

    }


    // dataset.foreach()

    //  val duplicate = dataset.keyBy(tup => tup._1)
    //
    //  val matrixPairs =
    //    dataset
    //      .keyBy(tup => tup._1)
    //      .join(duplicate)
    //      .filter(f => (f._2._1._2 != f._2._2._2))


  }




}
