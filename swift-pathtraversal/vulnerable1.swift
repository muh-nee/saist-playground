import Foundation

func readUpload(named name: String) throws -> Data {
    try Data(contentsOf: URL(fileURLWithPath: "/var/app/uploads/\(name)")) // VULNERABLE: ../ escapes uploads
}
