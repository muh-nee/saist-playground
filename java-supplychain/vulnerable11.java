import org.tensorflow.SavedModelBundle;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModelLoader {
    private static final String MODEL_URL = "https://storage.example.com/models/saved_model.zip";

    public SavedModelBundle load() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(MODEL_URL)).build();
        byte[] archiveBytes = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        String exportDir = System.getProperty("java.io.tmpdir") + "/saved_model";
        Files.createDirectories(Paths.get(exportDir));
        try (ZipInputStream zis = new ZipInputStream(new java.io.ByteArrayInputStream(archiveBytes))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String outPath = exportDir + "/" + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(outPath)) {
                    zis.transferTo(fos);
                }
            }
        }
        return SavedModelBundle.load(exportDir, "serve");
    }
}
