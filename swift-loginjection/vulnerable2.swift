import Foundation
import Vapor

func writeAuditEntry(_ request: Request) throws {
    let action = try request.content.get(String.self, at: "action")
    print("AUDIT action=\(action) status=accepted")
}
