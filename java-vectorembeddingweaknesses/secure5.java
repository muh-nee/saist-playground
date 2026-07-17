import io.milvus.client.MilvusServiceClient;
import io.milvus.param.dml.InsertParam;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MilvusStaticSeeder implements ApplicationRunner {
    private final MilvusServiceClient milvusClient;

    private static final List<String> KNOWLEDGE_BASE = List.of(
            "Java is a statically typed, object-oriented programming language.",
            "Spring Boot simplifies Java application development with auto-configuration.",
            "Milvus is an open-source vector database built for scalable similarity search."
    );

    public MilvusStaticSeeder(MilvusServiceClient milvusClient) {
        this.milvusClient = milvusClient;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (String content : KNOWLEDGE_BASE) {
            float[] vec = {0.1f, 0.2f, 0.3f};
            InsertParam insertParam = InsertParam.newBuilder()
                    .withCollectionName("knowledge")
                    .withFields(List.of(
                            new InsertParam.Field("content", List.of(content)),
                            new InsertParam.Field("embedding", List.of(vec))
                    ))
                    .build();
            milvusClient.insert(insertParam);
        }
    }
}
