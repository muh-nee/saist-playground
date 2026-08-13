import Vapor

func finishLogin(_ request: Request) throws -> Response {
    let next = request.query[String.self, at: "next"] ?? "/"
    guard next.hasPrefix("/"), !next.hasPrefix("//") else { return request.redirect(to: "/") }
    return request.redirect(to: next)
}
