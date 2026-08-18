import Foundation
import Vapor

func allocateRemainingQuota(_ request: Request) throws -> [UInt8] {
    let limit = try request.query.get(UInt.self, at: "limit")
    let used = try request.query.get(UInt.self, at: "used")
    let remaining = limit &- used
    return [UInt8](repeating: 0, count: Int(remaining))
}
