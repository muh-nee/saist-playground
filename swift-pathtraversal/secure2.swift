import Foundation

func readAvatar(named name: String) throws -> Data {
    guard name.allSatisfy({ $0.isLetter || $0.isNumber || $0 == "-" || $0 == "_" }) else {
        throw CocoaError(.fileNoSuchFile)
    }
    return try Data(contentsOf: URL(fileURLWithPath: "/var/app/avatars/\(name).png"))
}
