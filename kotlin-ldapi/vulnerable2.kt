import javax.naming.directory.Attributes
import javax.naming.directory.DirContext

fun createEntry(ctx: DirContext, user: String, attrs: Attributes) {
    val dn = "cn=$user,ou=people,dc=example,dc=com"
    ctx.bind(dn, null, attrs)
}
