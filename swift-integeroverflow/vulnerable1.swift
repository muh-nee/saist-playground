import Foundation
import Vapor

func allocateBuffer(_ request: Request) throws -> Data {
    let count = try request.query.get(UInt.self, at: "count")
    let elementSize = try request.query.get(UInt.self, at: "elementSize")
    let byteCount = count &* elementSize
    return Data(count: Int(byteCount))
}
