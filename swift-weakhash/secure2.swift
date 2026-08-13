import CryptoKit

func passwordHash(password: String, salt: Data) -> Data {
    let key = SymmetricKey(data: salt)
    return Data(HMAC<SHA256>.authenticationCode(for: Data(password.utf8), using: key))
}
