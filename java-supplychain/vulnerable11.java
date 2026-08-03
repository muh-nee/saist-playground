import org.tensorflow.SavedModelBundle;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

public class ModelLoader {
    private static final String MODEL_URL = "https://storage.example.com/models/saved_model.zip";

    public SavedModelBundle load() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(MODEL_URL)).build();
        byte[] archiveBytes = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        Files.write(Paths.get("/tmp/model.zip"), archiveBytes);
        String exportDir = "/tmp/saved_model";
        return SavedModelBundle.load(exportDir, "serve");
    }
}
