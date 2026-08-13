import Vapor

func greeting(_ request: Request) throws -> Response {
    let name = request.query[String.self, at: "name"] ?? "guest"
    return Response(status: .ok, headers: ["Content-Type": "text/html"], body: .init(string: "<h1>Hello, \(name)</h1>"))
}
