import Vapor

func redirect(_ request: Request) throws -> Response {
    let destination = try request.content.get(String.self, at: "returnTo")
    return request.redirect(to: destination, redirectType: .temporary) // VULNERABLE
}
