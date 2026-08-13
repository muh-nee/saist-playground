import Foundation

func convert(file: String) throws {
    let task = Process()
    task.launchPath = "/bin/sh"
    task.arguments = ["-c", "convert \(file) output.png"] // VULNERABLE: shell command construction
    try task.run()
}
