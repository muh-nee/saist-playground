import Vapor

func finishLogin(_ request: Request) throws -> Response {
    let next = request.query[String.self, at: "next"] ?? "/"
    return request.redirect(to: next)
}
