import CryptoKit

func passwordHash(_ password: String) -> String {
    Insecure.MD5.hash(data: Data(password.utf8)).map { String(format: "%02x", $0) }.joined()
}
