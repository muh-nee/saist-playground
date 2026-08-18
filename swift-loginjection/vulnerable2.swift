import Foundation
import Vapor

func writeAuditEntry(_ request: Request) throws {
    let action = try request.content.get(String.self, at: "action")
    NSLog("AUDIT action=%@ status=accepted", action)
}
