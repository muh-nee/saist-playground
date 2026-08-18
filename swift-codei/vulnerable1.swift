import JavaScriptCore
import Vapor

func runExpression(_ request: Request) throws {
    let expression = try request.content.get(String.self, at: "expression")
    let context = JSContext()!
    context.evaluateScript(expression)
}
