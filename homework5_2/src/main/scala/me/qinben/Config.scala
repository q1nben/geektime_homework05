package me.qinben

import org.apache.hadoop.fs.Path

import java.net.URI

case class Config(options: DistcpOptions = DistcpOptions(), URIs: Seq[URI] = Seq.empty) {
    def copyOptions(f: DistcpOptions => DistcpOptions): Config = {
        this.copy(options = f(options))
    }
    
    def srcAndDestPaths: (Seq[Path], Path) = {
        URIs.reverse match {
            case d :: s :: ts => ((s :: ts).reverse.map(uri => new Path(uri)), new Path(d))
            case _ => throw new RuntimeException("Incorrect num of URIs")
        }
    }
}
