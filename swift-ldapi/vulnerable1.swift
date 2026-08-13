import OpenLDAP

func findUser(connection: OpaquePointer, username: String) {
    let filter = "(uid=\(username))"
    ldap_search_ext_s(connection, "ou=users,dc=example,dc=com", LDAP_SCOPE_SUBTREE, filter, nil, 0, nil, nil, nil, 0, nil) // VULNERABLE: LDAP filter injection
}
