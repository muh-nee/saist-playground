import Vapor

func login(_ request: Request) async throws -> Response {
    do {
        try await authenticate(request)
        return Response(status: .ok)
    } catch {
        return Response(status: .internalServerError, body: .init(string: error.localizedDescription)) // VULNERABLE: exposes internal error
    }
}
