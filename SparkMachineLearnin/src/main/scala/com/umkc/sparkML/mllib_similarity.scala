package com.umkc.sparkML

import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
  * Created by hhstm4 on 4/29/2016.
  */
object mllib_similarity {


  System.setProperty("hadoop.home.dir","c:\\winutils")
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)


  val conf = new SparkConf()
    .setAppName("MovieLensALS")
    .set("spark.executor.memory", "2g").setMaster("local[*]")
  val sc = new SparkContext(conf)


  val input = sc.textFile("ml-100k//ua.base").map(line => line.split("\t").toSeq)

  val word2vec = new Word2Vec()

  val model = word2vec.fit(input)

  val synonyms = model.findSynonyms("china", 40)

  for((synonym, cosineSimilarity) <- synonyms) {
    println(s"$synonym $cosineSimilarity")
  }

  // Save and load model
  model.save(sc, "myModelPath")
  val sameModel = Word2VecModel.load(sc, "myModelPath")

}
