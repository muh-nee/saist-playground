import Foundation

func convert(file: String) throws {
    let task = Process()
    task.launchPath = "/bin/sh"
    task.arguments = ["-c", "convert \(file) output.png"]
    try task.run()
}
