import CommonCrypto

func encrypt(_ plaintext: Data, key: Data, iv: Data) -> Data? {
    var output = [UInt8](repeating: 0, count: plaintext.count + kCCBlockSizeDES)
    var written = 0
    CCCrypt(CCOperation(kCCEncrypt), CCAlgorithm(kCCAlgorithmDES), CCOptions(kCCOptionPKCS7Padding),
            [UInt8](key), key.count, [UInt8](iv), [UInt8](plaintext), plaintext.count,
            &output, output.count, &written)
    return Data(output.prefix(written))
}
