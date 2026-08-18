import Darwin

func sessionToken() -> String {
    String(format: "%08x", random())
}
