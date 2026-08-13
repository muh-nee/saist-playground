import Foundation
import ZIPFoundation

func extract(_ archive: Archive, to destination: URL) throws {
    for entry in archive {
        try archive.extract(entry, to: destination.appendingPathComponent(entry.path))
    }
}
