import Vapor

func importFile(_ request: Request) throws -> Response {
    do {
        try loadConfiguration()
        return Response(status: .ok)
    } catch {
        request.logger.error("Import failed: \(error)")
        throw Abort(.badRequest, reason: "Invalid import file.")
    }
}
