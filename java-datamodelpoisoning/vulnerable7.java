package main;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainController {
    private final MultiLayerNetwork model = loadModel();

    record TrainRequest(float[][] features, float[] labels) {}

    @PostMapping("/train")
    public String train(@RequestBody TrainRequest req) {
        model.fit(new DataSet(Nd4j.create(req.features()), Nd4j.create(req.labels())));
        return "trained";
    }

    private static MultiLayerNetwork loadModel() { return null; }
}
