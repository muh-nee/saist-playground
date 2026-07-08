import javax.naming.directory.DirContext
import javax.naming.directory.SearchControls
import org.springframework.ldap.support.LdapEncoder

fun findUser(ctx: DirContext, username: String) {
    val filter = "(&(uid=${LdapEncoder.filterEncode(username)})(objectClass=person))"
    ctx.search("ou=people,dc=example,dc=com", filter, SearchControls())
}
