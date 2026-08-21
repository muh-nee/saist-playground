import Foundation
import OpenAI

let client = OpenAI(apiToken: ProcessInfo.processInfo.environment["OPENAI_API_KEY"]!)
let approvedPackages: Set<String> = [
    "https://github.com/apple/swift-argument-parser",
    "https://github.com/vapor/vapor",
    "https://github.com/MacPaw/OpenAI",
]

func installApprovedPackage(task: String) async throws {
    let query = ChatQuery(
        messages: [.user(.init(content: .string("What Swift package URL for: \(task)? Reply with only the GitHub URL.")))],
        model: .gpt4_o
    )
    let result = try await client.chats(query: query)
    let packageURL = result.choices.first?.message.content?.string?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
    guard approvedPackages.contains(packageURL) else {
        throw NSError(domain: "PackageNotApproved", code: 1)
    }
    let process = Process()
    process.executableURL = URL(fileURLWithPath: "/usr/bin/swift")
    process.arguments = ["package", "add", packageURL]
    try process.run()
    process.waitUntilExit()
}
