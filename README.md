# geektime_homework05
## 1. 基本信息

学号: G20210735010190

## 2. 思路及运行截图
### 作业一
- 使用wholeTextFiles获取RDD，键为文件名，值为文件内容字符串
- 将文件名进行切分（去除目录名），将值按行切分再将每行按空格切分并拍扁，得到键为文件名，值为单词序列的RDD
- 将RDD转换为文件名及单词为键，值为1的RDD并根据键做聚合，将值转换为文件中出现单词的频次
- 将RDD转换为以单词为键，文件名及单词频次为值的RDD（类似二次排序的转换键的过程）并根据键进行聚合，将值转换为文件名、单词频次元组的序列，最后进行升序排序
- 将RDD拉取到本地（driver端或者client端）并进行格式化打印

#### 输入文件
![image](https://github.com/q1nben/geektime_homework05/blob/master/img/in.png)

#### IDEA运行截图
![image](https://github.com/q1nben/geektime_homework05/blob/master/img/ide.png)
P.S. `args(0)`为本地路径`/home/emr/code/scala/files`，内含文件0、1、2，与作业例子内容一致

#### Spark-Shell运行过程及截图
1. 使用命令`hadoop fs -put /home/emr/code/scala/files /user/student/files`
2. 运行命令`spark-shell --master yarn`
3. 使用:paste复制scala代码
```
val textFileRDD = sc.wholeTextFiles("/user/student/files")
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
```
4. ctrl+D结束复制
![image](https://github.com/q1nben/geektime_homework05/blob/master/img/spark-shell.png)


### 作业二

还在写
