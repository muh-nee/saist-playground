fun runReport(filename: String) {
    val pb = ProcessBuilder("bash", "-c", "wc -l $filename")
    pb.start()
}
