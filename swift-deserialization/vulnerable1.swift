import Foundation

func restoreSession(_ data: Data) -> Any? {
    NSKeyedUnarchiver.unarchiveObject(with: data) // VULNERABLE: accepts arbitrary archived classes
}
