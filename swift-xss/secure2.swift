import Vapor

func profile(_ request: Request) -> Response {
    let response = Response(status: .ok)
    response.headers.contentType = .json
    response.body = .init(string: "{\"status\":\"ok\"}") // SAFE: no HTML context
    return response
}
