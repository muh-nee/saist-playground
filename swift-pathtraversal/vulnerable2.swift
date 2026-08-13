import Foundation

func renderTemplate(named name: String) throws -> String {
    try String(contentsOfFile: "templates/\(name).html", encoding: .utf8) // VULNERABLE
}
