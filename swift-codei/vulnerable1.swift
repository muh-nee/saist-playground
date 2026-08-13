import JavaScriptCore

func runExpression(_ expression: String) {
    let context = JSContext()!
    context.evaluateScript(expression)
}
