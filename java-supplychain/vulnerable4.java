import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModelLoader {
    private static final String MODEL_URL = "https://storage.example.com/models/embedder.onnx";

    public OrtSession load() throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(MODEL_URL).build();
        Response response = client.newCall(request).execute();
        byte[] modelBytes = response.body().bytes();
        return env.createSession(modelBytes);
    }
}
