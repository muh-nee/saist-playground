import javax.naming.directory.DirContext
import javax.naming.directory.SearchControls
import javax.servlet.http.HttpServletRequest

fun searchByMail(ctx: DirContext, request: HttpServletRequest) {
    val mail = request.getParameter("mail")
    ctx.search("dc=example,dc=com", "(mail=$mail)", SearchControls())
}
