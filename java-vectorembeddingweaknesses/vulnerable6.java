import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Points.PointStruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static io.qdrant.client.PointIdFactory.id;
import static io.qdrant.client.ValueFactory.value;
import static io.qdrant.client.VectorsFactory.vectors;

@Path("/ingest")
public class QdrantIngestResource {
    private final QdrantClient client;

    public QdrantIngestResource(QdrantClient client) {
        this.client = client;
    }

    @POST
    public Response ingest(@QueryParam("text") String text) throws ExecutionException, InterruptedException {
        float[] vec = {0.1f, 0.2f, 0.3f};
        PointStruct point = PointStruct.newBuilder()
                .setId(id(UUID.randomUUID()))
                .setVectors(vectors(vec))
                .putAllPayload(java.util.Map.of("text", value(text)))
                .build();
        client.upsertAsync("docs", List.of(point), null).get();
        return Response.ok().build();
    }
}
