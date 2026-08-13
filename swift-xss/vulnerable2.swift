import Vapor

func preview(_ request: Request) throws -> Response {
    let content = try request.content.get(String.self, at: "content")
    return Response(status: .ok, headers: ["Content-Type": "text/html"], body: .init(string: content))
}
