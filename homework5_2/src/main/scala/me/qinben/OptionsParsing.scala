package me.qinben

import org.apache.hadoop.conf.Configuration
import scopt.OptionParser

import java.net.URI

object OptionsParsing {
    def parse(args: Array[String]) : Config = {
        val parser = new scopt.OptionParser[Config]("DistcpByScalaArgs") {
            opt[Unit]("i")
              .action((_, c) => c.copyOptions(_.copy(ignoreFailures = true)))
              .text("Ignore Failures")
            opt[Int]("maxConcurrences")
              .action((i, c) => c.copyOptions(_.copy(maxConcurrences = i)))
              .text("Maximum number of concurrences")
            help("help").text("print this usage text")
            arg[String]("[source_path...] <target_path>")
              .unbounded()
              .minOccurs(2)
              .action((u, c) => c.copy(URIs = c.URIs :+ new URI(u)))
        }
        parser.parse(args, Config()) match {
            case Some(config) =>
                config.options.validateOptions()
                config
            case _ =>
                throw new RuntimeException("Failed to parse arguments")
        }
    }
}