import OpenLDAP

func findGroup(connection: OpaquePointer, group: String) {
    ldap_search_ext_s(connection, "ou=groups,dc=example,dc=com", LDAP_SCOPE_SUBTREE, "(cn=\(group))", nil, 0, nil, nil, nil, 0, nil)
}
