package com.umkc.sparkML

/**
  * Created by hhstm4 on 4/6/2016.
  */
import org.apache.spark.{SparkConf, SparkContext}
// $example on$
import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
// $example off$

object SimpleFPGrowth {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","c:\\winutils")

    val conf =new SparkConf().setMaster("local[*]").setAppName("SparkNaiveBayes").set("spark.driver.memory", "3g").set("spark.executor.memory", "3g")
    val sc = new SparkContext(conf)
   // val conf = new SparkConf().setAppName("SimpleFPGrowth")
    //val sc = new SparkContext(conf)

    // $example on$
    val data = sc.textFile("ml-100k/sample_fpgrowth.txt")

    val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))

    val fpg = new FPGrowth()
      .setMinSupport(0.2)
      .setNumPartitions(10)
    val model = fpg.run(transactions)

    model.freqItemsets.collect().foreach { itemset =>
      println(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq)
    }

//    val minConfidence = 0.8
//    model.generateAssociationRules(minConfidence).collect().foreach { rule =>
//      println(
//        rule.antecedent.mkString("[", ",", "]")
//          + " => " + rule.consequent .mkString("[", ",", "]")
//          + ", " + rule.confidence)
//    }
    // $example off$
  }
}
// scalastyle:on println