import CommonCrypto

func checksum(_ data: Data) -> Data {
    var digest = [UInt8](repeating: 0, count: Int(CC_SHA1_DIGEST_LENGTH))
    CC_SHA1([UInt8](data), CC_LONG(data.count), &digest) // VULNERABLE: SHA-1
    return Data(digest)
}
