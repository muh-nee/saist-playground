import Foundation

func ping(host: String) throws {
    let process = Process()
    process.executableURL = URL(fileURLWithPath: "/bin/sh")
    process.arguments = ["-c", "ping -c 1 \(host)"] // VULNERABLE: host reaches a shell
    try process.run()
}
