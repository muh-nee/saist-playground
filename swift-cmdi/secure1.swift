import Foundation

func ping(host: String) throws {
    let process = Process()
    process.executableURL = URL(fileURLWithPath: "/sbin/ping")
    process.arguments = ["-c", "1", host]
    try process.run()
}
