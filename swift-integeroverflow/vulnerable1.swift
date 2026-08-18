import Foundation
import Vapor

func allocateBuffer(_ request: Request) throws -> Data {
    let count = try request.query.get(UInt.self, at: "count")
    let elementSize = try request.query.get(UInt.self, at: "elementSize")
    let byteCount = count &* elementSize
    let allocationSize = UInt32(truncatingIfNeeded: byteCount)
    return Data(count: Int(allocationSize))
}
