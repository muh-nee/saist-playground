import Foundation

func remainingQuota(limit: Int, used: Int) -> Int {
    limit &- used
}
