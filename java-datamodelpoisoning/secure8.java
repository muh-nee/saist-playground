import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.web.bind.annotation.*;
import java.net.*;
import java.net.http.*;
import java.security.MessageDigest;

@RestController
public class TrainController {
    private final MultiLayerNetwork model = buildModel();
    private static final String EXPECTED_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private static final String TRUSTED_URL = "https://internal.example.com/datasets/approved.json";

    @PostMapping("/train-verified")
    public String trainVerified() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(new URI(TRUSTED_URL)).GET().build();
        byte[] body = client.send(req, HttpResponse.BodyHandlers.ofByteArray()).body();
        String actual = bytesToHex(MessageDigest.getInstance("SHA-256").digest(body));
        if (!actual.equals(EXPECTED_SHA256)) return "integrity check failed";
        float[][] features = parseFeatures(new String(body));
        model.fit(new DataSet(Nd4j.create(features), Nd4j.create(new float[features.length][1])));
        return "trained";
    }
}
