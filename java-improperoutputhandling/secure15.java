import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SummaryController {
	private final OpenAiChatModel model = OpenAiChatModel.builder()
		.apiKey(System.getenv("OPENAI_API_KEY"))
		.modelName("gpt-4o-mini")
		.build();

	@GetMapping("/summary")
	public ResponseEntity<Map<String, String>> getSummary() {
		String content = model.generate("Summarize the latest AI news in Markdown.");
		return ResponseEntity.ok()
			.header("Content-Security-Policy", "img-src 'self'")
			.body(Map.of("content", content));
	}
}
