import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.model.chat.ChatLanguageModel;
import java.sql.ResultSet;

public class vulnerable8 {
    interface SupportAssistant {
        @UserMessage("Summarize support history for customer {{email}} with SSN {{ssn}}.")
        String summarize(@V("email") String email, @V("ssn") String ssn);
    }

    private ChatLanguageModel chatLanguageModel;
    private ResultSet resultSet;

    public String handleTicket() throws Exception {
        String email = resultSet.getString("email");
        String ssn = resultSet.getString("ssn");
        SupportAssistant assistant = AiServices.create(SupportAssistant.class, chatLanguageModel);
        return assistant.summarize(email, ssn);
    }
}
