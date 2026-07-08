import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.SimpleEvaluationContext

fun applyDiscount(order: Any): Any? {
    val parser = SpelExpressionParser()
    val expr = parser.parseExpression("total * 0.9")
    val ctx = SimpleEvaluationContext.forReadOnlyDataBinding().build()
    return expr.getValue(ctx, order)
}
