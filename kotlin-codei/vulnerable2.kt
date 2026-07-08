import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RuleController {
    private val parser = SpelExpressionParser()

    @PostMapping("/eval")
    fun eval(@RequestParam expr: String): Any? {
        return parser.parseExpression(expr).value
    }
}
