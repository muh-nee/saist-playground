import Foundation

func allocationSize(count: UInt, elementSize: UInt) throws -> UInt {
    let (size, overflow) = count.multipliedReportingOverflow(by: elementSize)
    guard !overflow else { throw CocoaError(.fileWriteOutOfSpace) }
    return size
}
