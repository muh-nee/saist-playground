import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.web.bind.annotation.*;
import java.net.*;
import java.net.http.*;

@RestController
public class TrainController {
    private final MultiLayerNetwork model = buildModel();

    @PostMapping("/train-from-url")
    public String trainFromUrl(@RequestParam String datasetUrl) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(new URI(datasetUrl)).GET().build();
        String body = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
        float[][] features = parseFeatures(body);
        float[][] labels = parseLabels(body);
        model.fit(new DataSet(Nd4j.create(features), Nd4j.create(labels)));
        return "trained";
    }
}
