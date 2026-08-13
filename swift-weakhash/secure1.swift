import CryptoKit

func digest(_ value: String) -> SHA256.Digest {
    SHA256.hash(data: Data(value.utf8))
}
