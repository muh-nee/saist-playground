import io.weaviate.client.WeaviateClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/ingest")
public class NamespacedWeaviateResource {
    private final WeaviateClient client;

    public NamespacedWeaviateResource(WeaviateClient client) {
        this.client = client;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ingest(@Context SecurityContext securityContext, @QueryParam("text") String text) {
        String className = "User_" + securityContext.getUserPrincipal().getName().replaceAll("[^a-zA-Z0-9]", "_");
        Map<String, Object> props = new HashMap<>();
        props.put("content", text);
        client.data().creator().withClassName(className).withProperties(props).run();
        return Response.ok().build();
    }
}
