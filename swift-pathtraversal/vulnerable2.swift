import Foundation
import Vapor

func renderTemplate(_ request: Request) throws -> String {
    let name = try request.content.get(String.self, at: "template")
    try String(contentsOfFile: "templates/\(name).html", encoding: .utf8)
}
