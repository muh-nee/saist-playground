import Foundation

func writeAuditEntry(action: String) {
    print("AUDIT action=\(action) status=accepted") // VULNERABLE: untrusted log field
}
