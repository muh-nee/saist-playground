import OpenLDAP

func findGroup(connection: OpaquePointer, groupID: UUID) {
    ldap_search_ext_s(connection, "ou=groups,dc=example,dc=com", LDAP_SCOPE_SUBTREE, "(entryUUID=\(groupID.uuidString))", nil, 0, nil, nil, nil, 0, nil)
}
