import Foundation
import ZIPFoundation

func extract(_ archive: Archive, to destination: URL) throws {
    let base = destination.standardizedFileURL
    for entry in archive {
        let target = base.appendingPathComponent(entry.path).standardizedFileURL
        guard target.path.hasPrefix(base.path + "/") else { continue }
        try archive.extract(entry, to: target)
    }
}
