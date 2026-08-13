import CryptoKit

func decrypt(_ combined: Data, key: SymmetricKey) throws -> Data {
    let box = try AES.GCM.SealedBox(combined: combined)
    return try AES.GCM.open(box, using: key)
}
