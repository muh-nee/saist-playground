import ai.djl.Application;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import java.nio.file.Paths;

public class ModelLoader {
    public ZooModel<float[], float[]> load() throws Exception {
        Criteria<float[], float[]> criteria = Criteria.builder()
            .setTypes(float[].class, float[].class)
            .optModelPath(Paths.get("./models/resnet50"))
            .optApplication(Application.CV.IMAGE_CLASSIFICATION)
            .build();
        return criteria.loadModel();
    }
}
