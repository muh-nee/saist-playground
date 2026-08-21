import Foundation
import CoreML
import OpenAI

let client = OpenAI(apiToken: ProcessInfo.processInfo.environment["OPENAI_API_KEY"]!)

func loadRecommendedModel(task: String) async throws -> MLModel {
    let query = ChatQuery(
        messages: [.user(.init(content: .string("What CoreML model URL should I download for: \(task)? Reply with only the URL.")))],
        model: .gpt4_o
    )
    let result = try await client.chats(query: query)
    let modelURLString = result.choices.first?.message.content?.string?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
    let modelURL = URL(string: modelURLString)!
    let (localURL, _) = try await URLSession.shared.download(from: modelURL)
    return try MLModel(contentsOf: localURL)
}
