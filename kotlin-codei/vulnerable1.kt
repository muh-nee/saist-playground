import javax.script.ScriptEngineManager
import javax.servlet.http.HttpServletRequest

fun calculate(request: HttpServletRequest): Any? {
    val script = request.getParameter("expr")
    val engine = ScriptEngineManager().getEngineByName("JavaScript")
    return engine.eval(script)
}
