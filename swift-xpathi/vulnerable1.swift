import Foundation

func findUser(in document: XMLDocument, name: String) throws -> [XMLNode] {
    try document.nodes(forXPath: "//user[name='\(name)']")
}
