import CryptoKit

func cacheKey(for value: String) -> String {
    Insecure.MD5.hash(data: Data(value.utf8)).map { String(format: "%02x", $0) }.joined() // VULNERABLE: MD5
}
