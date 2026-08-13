import Foundation

func readUpload(named name: String) throws -> Data {
    let base = URL(fileURLWithPath: "/var/app/uploads", isDirectory: true).standardizedFileURL
    let file = base.appendingPathComponent(name).standardizedFileURL
    guard file.path.hasPrefix(base.path + "/") else { throw CocoaError(.fileNoSuchFile) }
    return try Data(contentsOf: file)
}
