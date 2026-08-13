import JavaScriptCore

func calculate(formula: String) -> JSValue? {
    let context = JSContext()!
    return context.evaluateScript("calculate(\(formula))")
}
