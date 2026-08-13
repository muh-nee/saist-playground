import Foundation
import ZIPFoundation

func extractEntry(_ archive: Archive, entry: Archive.Entry, to destination: URL) throws {
    let target = destination.appendingPathComponent(entry.path)
    try archive.extract(entry, to: target)
}
