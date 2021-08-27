package me.qinben
import me.qinben.utils.Utils
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.SparkSession

object DistcpByScala {

    def main(args: Array[String]): Unit = {
        val sparkSession = SparkSession.builder().getOrCreate()

        val conf = OptionsParsing.parse(args)

        val (src, dest) = conf.srcAndDestPaths

        run(sparkSession, src, dest, conf.options)
    }

    def run(sparkSession: SparkSession, src: Seq[Path], dest: Path, options: DistcpOptions): Unit = {
        import sparkSession.implicits._

        options.validateOptions()

        val qualifiedSourcePaths = src.map(Utils.pathToQualifiedPath(sparkSession.sparkContext.hadoopConfiguration, _))
        val qualifiedDestinationPath = Utils.pathToQualifiedPath(sparkSession.sparkContext.hadoopConfiguration, dest)

        val sourceRDD =
    }
}
