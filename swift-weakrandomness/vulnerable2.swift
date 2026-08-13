import Darwin

func sessionToken() -> String {
    String(format: "%08x", lrand48()) // VULNERABLE: predictable session token
}
