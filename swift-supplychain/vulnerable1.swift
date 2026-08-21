import Foundation
import CoreML
import OpenAI

let client = OpenAI(apiToken: ProcessInfo.processInfo.environment["OPENAI_API_KEY"]!)

func installSuggestedPackageAndLoadModel(task: String, modelURL: URL) async throws -> MLModel {
    let query = ChatQuery(
        messages: [.user(.init(content: .string("What Swift package should I use for: \(task)? Reply with only the GitHub URL.")))],
        model: .gpt4_o
    )
    let result = try await client.chats(query: query)
    let packageURL = result.choices.first?.message.content?.string?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
    let process = Process()
    process.executableURL = URL(fileURLWithPath: "/usr/bin/swift")
    process.arguments = ["package", "add", packageURL]
    try process.run()
    process.waitUntilExit()

    let (localURL, _) = try await URLSession.shared.download(from: modelURL)
    return try MLModel(contentsOf: localURL)
}
