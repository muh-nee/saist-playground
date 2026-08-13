import Vapor

func redirectToDashboard(_ request: Request) -> Response {
    request.redirect(to: "/dashboard")
}
