import Vapor

func greeting(_ request: Request) throws -> Response {
    let name = request.query[String.self, at: "name"] ?? "guest"
    let escaped = name.replacing("&", with: "&amp;").replacing("<", with: "&lt;").replacing(">", with: "&gt;").replacing("\"", with: "&quot;").replacing("'", with: "&#39;")
    return Response(status: .ok, headers: ["Content-Type": "text/html"], body: .init(string: "<h1>Hello, \(escaped)</h1>"))
}
