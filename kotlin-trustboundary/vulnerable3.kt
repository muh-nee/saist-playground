import javax.servlet.http.HttpServletRequest

fun trackReferral(request: HttpServletRequest) {
    val ref = request.cookies?.firstOrNull { it.name == "ref" }?.value ?: ""
    request.session.setAttribute("referral", ref)
}
