import Security

func resetCode() throws -> Int {
    var value: UInt32 = 0
    guard SecRandomCopyBytes(kSecRandomDefault, MemoryLayout.size(ofValue: value), &value) == errSecSuccess else { throw CocoaError(.coderInvalidValue) }
    return Int(value % 900_000) + 100_000
}
