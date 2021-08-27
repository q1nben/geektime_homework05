package me.qinben.utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

object Utils {
    def pathToQualifiedPath(hadoopConf: Configuration, path: Path): Path = {
        val fs = FileSystem.get(hadoopConf)
        path.makeQualified(fs.getUri, fs.getWorkingDirectory)
    }

    def checkDirectories(hadoopConf: Configuration, srcPath: Path, destPath: Path, files: Seq[Path]): Unit = {
        val fs = FileSystem.get(hadoopConf)
        fs.listLocatedStatus()
    }
}
