import ai.djl.Application;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;

public class ModelLoader {
    private static final String MODEL_URL = "https://models.example.com/resnet50.zip";

    public ZooModel<float[], float[]> load() throws Exception {
        Criteria<float[], float[]> criteria = Criteria.builder()
            .setTypes(float[].class, float[].class)
            .optModelUrls(MODEL_URL)
            .optApplication(Application.CV.IMAGE_CLASSIFICATION)
            .build();
        return criteria.loadModel();
    }
}
