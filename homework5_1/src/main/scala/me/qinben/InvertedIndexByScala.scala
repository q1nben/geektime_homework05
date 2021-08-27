package me.qinben

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 词频倒排索引
 */
object InvertedIndexByScala {
    def main(args: Array[String]): Unit = {
        // 配置SparkConf，设置AppName及Master
        val conf = new SparkConf().setAppName("InvertedIndex").setMaster("local") // 本机运行
        // val conf = new SparkConf().setAppName("InvertedIndex") // 集群运行
        // 创建SparkContext
        val sc = new SparkContext(conf)
        // 读取文件，获取的RDD键为文件名字符串，值为文件的完整内容字符串
        val textFileRDD = sc.wholeTextFiles(args(0))
        // 对RDD进行处理，获取结果
        val resultsRDD = textFileRDD.map(file => (file._1.split("/").last, file._2.split(System.lineSeparator()) // 对文件名切分，并将完整内容按行切分
          .flatMap(line => line.split(" ")))) // 将每行文本以空格为分割符切分并拍扁，键为去除路径的文件名，值为单词序列
          .flatMap(line => {
              line._2.map(word => ((word, line._1), 1)) // 将单词及文件名作为键，单词次数作为值
          })
          .reduceByKey(_ + _) // 根据键值做聚合
          .map(kv => {
              val word = kv._1._1
              val filename = kv._1._2
              val count = kv._2
              (word, (filename, count)) // 以单词为键，文件名及词频作为值
          })
          .groupByKey // 根据键值做聚合，将值变成Iterable[(String, Int)]
          .sortByKey(true) // 升序排序
        // 拉取到本地
        val results = resultsRDD.collect
        // 格式化打印
        results.foreach(result => {
            printf("\"%s\":\t", result._1)
            println(result._2.mkString("{", ",", "}"))
        })
    }
}
