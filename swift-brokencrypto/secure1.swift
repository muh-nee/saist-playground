import CryptoKit

func encrypt(_ plaintext: Data, key: SymmetricKey) throws -> AES.GCM.SealedBox {
    try AES.GCM.seal(plaintext, using: key) // SAFE: CryptoKit generates a fresh nonce
}
