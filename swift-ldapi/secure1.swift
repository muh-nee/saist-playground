import OpenLDAP

func findUser(connection: OpaquePointer, username: String) {
    guard username.allSatisfy({ $0.isLetter || $0.isNumber || $0 == "_" }) else { return }
    ldap_search_ext_s(connection, "ou=users,dc=example,dc=com", LDAP_SCOPE_SUBTREE, "(uid=\(username))", nil, 0, nil, nil, nil, 0, nil)
}
