import io.milvus.client.MilvusServiceClient;
import io.milvus.param.dml.InsertParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class MilvusIngestServlet extends jakarta.servlet.http.HttpServlet {
    private final MilvusServiceClient milvusClient;

    public MilvusIngestServlet(MilvusServiceClient milvusClient) {
        this.milvusClient = milvusClient;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String text = req.getParameter("text");
        float[] vec = {0.1f, 0.2f, 0.3f};
        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName("docs")
                .withFields(List.of(
                        new InsertParam.Field("content", List.of(text)),
                        new InsertParam.Field("embedding", List.of(vec))
                ))
                .build();
        milvusClient.insert(insertParam);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
