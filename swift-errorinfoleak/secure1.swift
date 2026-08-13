import Vapor

func login(_ request: Request) async throws -> Response {
    do {
        try await authenticate(request)
        return Response(status: .ok)
    } catch {
        request.logger.error("Authentication failed: \(error)")
        return Response(status: .internalServerError, body: .init(string: "Unable to sign in."))
    }
}
