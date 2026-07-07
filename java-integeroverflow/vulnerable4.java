import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    record Request(@JsonProperty("count") int count, @JsonProperty("size") int size) {}

    @PostMapping("/data")
    public byte[] processData(@RequestBody Request req) {
        return new byte[req.count() * req.size()]; // overflow if product exceeds Integer.MAX_VALUE
    }
}
