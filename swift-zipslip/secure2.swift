import Foundation
import ZIPFoundation

func extractFlat(_ archive: Archive, to destination: URL) throws {
    for entry in archive {
        let name = URL(fileURLWithPath: entry.path).lastPathComponent
        guard !name.isEmpty else { continue }
        try archive.extract(entry, to: destination.appendingPathComponent(name))
    }
}
