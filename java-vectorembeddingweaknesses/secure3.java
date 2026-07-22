import io.weaviate.client.WeaviateClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/ingest")
public class AdminWeaviateResource {
    private final WeaviateClient client;

    public AdminWeaviateResource(WeaviateClient client) {
        this.client = client;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response ingest(@FormParam("text") String text, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("ADMIN")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Map<String, Object> props = new HashMap<>();
        props.put("content", text);
        client.data().creator().withClassName("Article").withProperties(props).run();
        return Response.ok().build();
    }
}
