import OpenLDAP
import Vapor

func findGroup(_ request: Request, connection: OpaquePointer) throws {
    let group = try request.content.get(String.self, at: "group")
    let ldapSearchRequest = "(cn=\(group))"
    ldap_search_ext_s(connection, "ou=groups,dc=example,dc=com", LDAP_SCOPE_SUBTREE, ldapSearchRequest, nil, 0, nil, nil, nil, 0, nil)
}
