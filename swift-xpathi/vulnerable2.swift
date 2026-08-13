import Foundation

func findProduct(in document: XMLDocument, id: String) throws -> [XMLNode] {
    try document.nodes(forXPath: "//product[@id='\(id)']") // VULNERABLE
}
