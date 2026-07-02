import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import java.sql.ResultSet;

public class vulnerable11 {
    private ResultSet resultSet;

    public String analyzeAccount() throws Exception {
        String email = resultSet.getString("email");
        String dateOfBirth = resultSet.getString("date_of_birth");
        try (VertexAI vertexAI = new VertexAI("my-project", "us-central1")) {
            GenerativeModel model = new GenerativeModel("gemini-1.5-pro", vertexAI);
            GenerateContentResponse response = model.generateContent(
                    "Review account for " + email + " (DOB: " + dateOfBirth + ")"
            );
            return response.getCandidates(0).getContent().getParts(0).getText();
        }
    }
}
