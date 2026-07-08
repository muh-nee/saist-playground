import org.springframework.ldap.core.AttributesMapper
import org.springframework.ldap.core.LdapTemplate

class GroupService(private val ldap: LdapTemplate) {
    fun membersOf(group: String, mapper: AttributesMapper<String>): List<String> =
        ldap.search("", "(memberOf=cn=$group,ou=groups,dc=example,dc=com)", mapper)
}
