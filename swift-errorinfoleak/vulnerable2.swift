import Vapor

func importFile(_ request: Request) throws -> Response {
    do {
        try loadConfiguration()
        return Response(status: .ok)
    } catch {
        return Response(status: .badRequest, body: .init(string: "Import failed: \(error)")) // VULNERABLE: reflected diagnostic detail
    }
}
