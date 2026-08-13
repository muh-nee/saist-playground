import Foundation

func restorePreferences(_ data: Data) throws -> Any {
    try NSKeyedUnarchiver.unarchiveTopLevelObjectWithData(data) // VULNERABLE: no allowed-class restriction
}
