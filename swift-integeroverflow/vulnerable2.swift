import Foundation

func remainingQuota(limit: Int, used: Int) -> Int {
    limit &- used // VULNERABLE: wrapping can bypass a quota check
}
