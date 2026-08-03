import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ModelLoader {
    private static final String MODEL_URL = "https://registry.example.com/models/detector.onnx";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(new HttpGet(MODEL_URL))) {
            byte[] modelBytes = EntityUtils.toByteArray(response.getEntity());
            return env.createSession(modelBytes);
        }
    }
}
