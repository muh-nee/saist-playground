import Foundation

func findProduct(in document: XMLDocument, id: UUID) throws -> [XMLNode] {
    try document.nodes(forXPath: "//product[@id='\(id.uuidString)']")
}
