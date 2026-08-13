import Foundation

func remainingQuota(limit: Int, used: Int) throws -> Int {
    let (remaining, overflow) = limit.subtractingReportingOverflow(used)
    guard !overflow, remaining >= 0 else { throw CocoaError(.fileWriteNoPermission) }
    return remaining
}
