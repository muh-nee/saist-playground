import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.sql.ResultSet;

public class secure9 {
    private ResultSet resultSet;

    public String investigateCharges() throws Exception {
        String creditCard = resultSet.getString("credit_card");
        creditCard = "****-****-****-" + creditCard.substring(creditCard.length() - 4);
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
        return model.generate("Investigate recent charges for card " + creditCard);
    }
}
