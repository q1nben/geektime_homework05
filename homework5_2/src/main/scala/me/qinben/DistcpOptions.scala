package me.qinben

case class DistcpOptions(ignoreFailures: Boolean = false, maxConcurrences: Int = 20) {
    def validateOptions(): Unit = {
        assert(maxConcurrences > 0, "Max concurrences must be positive")
    }
}
