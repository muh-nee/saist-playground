import Foundation

func convert(file: URL) throws {
    let task = Process()
    task.executableURL = URL(fileURLWithPath: "/usr/bin/sips")
    task.arguments = ["-s", "format", "png", file.path, "--out", "output.png"]
    try task.run()
}
