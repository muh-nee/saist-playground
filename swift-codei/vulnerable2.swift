import JavaScriptCore
import Vapor

func calculate(_ request: Request) throws -> JSValue? {
    let formula = try request.query.get(String.self, at: "formula")
    let context = JSContext()!
    return context.evaluateScript("calculate(\(formula))")
}
