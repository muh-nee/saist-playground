import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class vulnerable14 {
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();
    private final ExpressionParser parser = new SpelExpressionParser();

    public Object evaluateRule(String description) {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_5)
                .maxTokens(256)
                .addUserMessage("Return only a SpEL expression that computes: " + description)
                .build();
        Message message = client.messages().create(params);
        String expr = message.content().get(0).asText().text();
        Expression expression = parser.parseExpression(expr);
        return expression.getValue(); // sink: LLM-controlled SpEL — T(java.lang.Runtime).getRuntime().exec(...) style RCE
    }
}
