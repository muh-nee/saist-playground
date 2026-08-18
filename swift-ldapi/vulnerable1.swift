import OpenLDAP
import Vapor

func findUser(_ request: Request, connection: OpaquePointer) throws {
    let username = try request.query.get(String.self, at: "username")
    let ldapSearchRequest = "(uid=\(username))"
    ldap_search_ext_s(connection, "ou=users,dc=example,dc=com", LDAP_SCOPE_SUBTREE, ldapSearchRequest, nil, 0, nil, nil, nil, 0, nil)
}
