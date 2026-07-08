fun runService(action: String) {
    val allowed = setOf("start", "stop", "status")
    require(action in allowed) { "invalid action" }
    ProcessBuilder("systemctl", action, "nginx").start()
}
