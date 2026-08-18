import Foundation
import Vapor

func readUpload(_ request: Request) throws -> Data {
    let name = try request.query.get(String.self, at: "name")
    try Data(contentsOf: URL(fileURLWithPath: "/var/app/uploads/\(name)"))
}
