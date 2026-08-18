import CommonCrypto

func verifyUpdateIntegrity(_ payload: Data, expectedDigest: Data) -> Bool {
    var digest = [UInt8](repeating: 0, count: Int(CC_SHA1_DIGEST_LENGTH))
    CC_SHA1([UInt8](payload), CC_LONG(payload.count), &digest)
    return Data(digest) == expectedDigest
}
