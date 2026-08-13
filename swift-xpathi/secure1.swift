import Foundation

func findUser(in document: XMLDocument, name: String) throws -> [XMLNode] {
    guard name.allSatisfy({ $0.isLetter || $0.isNumber || $0 == "_" }) else { return [] }
    return try document.nodes(forXPath: "//user[name='\(name)']")
}
