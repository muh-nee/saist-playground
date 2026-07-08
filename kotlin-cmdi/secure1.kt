fun ping(host: String) {
    ProcessBuilder("/bin/ping", "-c", "1", host).start()
}
