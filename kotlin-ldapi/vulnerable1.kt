import javax.naming.directory.DirContext
import javax.naming.directory.SearchControls

fun findUser(ctx: DirContext, username: String) {
    val filter = "(&(uid=$username)(objectClass=person))"
    ctx.search("ou=people,dc=example,dc=com", filter, SearchControls())
}
