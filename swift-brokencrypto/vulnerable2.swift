import CommonCrypto

func encryptProfile(_ plaintext: Data, key: Data) -> Data? {
    let staticIV = Data(repeating: 0, count: kCCBlockSizeAES128)
    var output = [UInt8](repeating: 0, count: plaintext.count + kCCBlockSizeAES128)
    var written = 0
    CCCrypt(CCOperation(kCCEncrypt), CCAlgorithm(kCCAlgorithmAES), CCOptions(kCCOptionPKCS7Padding),
            [UInt8](key), key.count, [UInt8](staticIV), [UInt8](plaintext), plaintext.count,
            &output, output.count, &written)
    return Data(output.prefix(written))
}
