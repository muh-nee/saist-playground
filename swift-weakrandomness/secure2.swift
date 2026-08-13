import CryptoKit

func sessionToken() -> String {
    Data(SymmetricKey(size: .bits256).withUnsafeBytes { $0 }).base64EncodedString()
}
