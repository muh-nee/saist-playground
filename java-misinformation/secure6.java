import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SummaryService {
    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey("API_KEY").build();
    private final List<String> summaries = new ArrayList<>();

    public void processSummary(String topic) {
        String summary = model.generate("Summarize: " + topic);
        summaries.add(summary);
        log.info("Summary stored, count={}", summaries.size());
    }
}
