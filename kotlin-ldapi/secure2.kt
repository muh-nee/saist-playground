import org.springframework.ldap.core.AttributesMapper
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.query.LdapQueryBuilder.query

class UserService(private val ldap: LdapTemplate) {
    fun byUid(uid: String, mapper: AttributesMapper<String>): List<String> {
        val q = query().where("uid").`is`(uid)
        return ldap.search(q, mapper)
    }
}
