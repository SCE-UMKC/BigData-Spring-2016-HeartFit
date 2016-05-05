package com.umkc.sparkML

/**
  * Created by hhstm4 on 4/6/2016.
  */

import java.io.{File, PrintWriter}
import java.sql.DriverManager
import java.text.SimpleDateFormat

import org.apache.spark.{SparkConf, SparkContext}
// $example on$
import org.apache.spark.mllib.fpm.{AssociationRules, FPGrowth}
import org.apache.spark.rdd.{JdbcRDD, RDD}
import java.util.{Date, Calendar}
import org.apache.spark.mllib.fpm.FPGrowth.FreqItemset
// $example off$

object SimpleFPGrowth {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","c:\\winutils")

    val conf = new SparkConf()
      .setAppName("pattern")
      .set("spark.executor.memory", "2g").setMaster("local[*]")
    val sc = new SparkContext(conf)
   // val conf = new SparkConf().setAppName("SimpleFPGrowth")
    //val sc = new SparkContext(conf)

    //mysql connection
    val databaseUsername = "heartfit_root"
    val databasePassword = "heart123"
    val databaseConnectionUrl = "jdbc:mysql://srv70.hosting24.com:3306/heartfit_123?user=" + databaseUsername + "&password=" + databasePassword

    val date = new Date();
    val readFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa").format(date);
    val writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(readFormat)

    val modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date)
    val modifiedhour = new SimpleDateFormat("hh").format(writeFormat)

    println(modifiedDate + " " + modifiedhour )

    val rdd = new JdbcRDD( sc, () =>
      DriverManager.getConnection(databaseConnectionUrl,databaseUsername,databasePassword) ,
      "select heart_rate,date from heart_rate limit ?, ?",
      1, 5000, 2, r => r.getString("heart_rate") + " " + r.getString("date") )

    // $example on$
    val data = sc.textFile("sample_fpgrowth.txt")

    val transactions: RDD[Array[String]] = rdd.map(s => s.trim.split(" "))

    val freqItemsets = transactions
      .flatMap(xs =>
        (xs.combinations(1) ++ xs.combinations(2)).map(x => (x.toList, 1L))
      )
      .reduceByKey(_ + _)
      .map{case (xs, cnt) => new FreqItemset(xs.toArray, cnt)}


    val rt = freqItemsets.map(itemset =>
      {
        val key =itemset.items.mkString("[", ",", "]")
        val value =   itemset.freq
        (key,value)

      })
      .filter(r => r._1.length() < 5)


  }
  case class ht(out_hr: Int,out_num: Int, out_date: String , out_hour: Int)

  def GetHour(hour : Int) = {

    val result = if(hour == 1)
      13
    else if(hour == 2)
      14
    else if(hour == 3)
      15
    else if(hour == 4)
      16
    else if(hour == 5)
      17
    else if(hour == 6)
      18
    else if(hour == 7)
      19
    else if(hour == 8)
      20
    else if(hour == 9)
      21
    else if(hour == 10)
      12
    else if(hour == 11)
      23

    result
  }
}
// scalastyle:on println