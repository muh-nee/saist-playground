import Foundation

func restoreProfile(_ data: Data) throws -> [String: String] {
    let allowed: Set<AnyHashable> = [NSDictionary.self, NSString.self]
    let object = try NSKeyedUnarchiver.unarchivedObject(ofClasses: allowed as? Set<AnyClass> ?? [], from: data)
    return object as? [String: String] ?? [:]
}
