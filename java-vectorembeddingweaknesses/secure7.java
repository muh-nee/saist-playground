import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
public class JwtRoleQdrantResource {
    private static final byte[] JWT_SECRET = "supersecretkey1234567890abcdef12".getBytes();
    private final QdrantClient client;

    public JwtRoleQdrantResource(QdrantClient client) {
        this.client = client;
    }

    @POST
    public Response ingest(@HeaderParam("Authorization") String authHeader, @QueryParam("text") String text)
            throws ExecutionException, InterruptedException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (!"ADMIN".equals(claims.get("role", String.class))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
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
