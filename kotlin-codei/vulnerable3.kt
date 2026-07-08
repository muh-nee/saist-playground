import groovy.lang.GroovyShell

fun runRule(code: String): Any? {
    val shell = GroovyShell()
    return shell.evaluate(code)
}
