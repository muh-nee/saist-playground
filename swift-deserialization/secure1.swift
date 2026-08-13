import Foundation

func restoreSession(_ data: Data) throws -> NSDictionary {
    try NSKeyedUnarchiver.unarchivedObject(ofClass: NSDictionary.self, from: data) ?? [:]
}
