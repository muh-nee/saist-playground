import JavaScriptCore

func renderTemplate(name: String) -> JSValue? {
    let context = JSContext()!
    context.setObject(name, forKeyedSubscript: "displayName" as NSString)
    return context.evaluateScript("render(displayName)") // SAFE: code is constant
}
