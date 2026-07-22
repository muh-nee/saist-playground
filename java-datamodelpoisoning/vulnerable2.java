package main;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
public class GraphController {
    record LoadRequest(String modelPath) {}

    @PostMapping("/load-graph")
    public String load(@RequestBody LoadRequest req) throws Exception {
        ComputationGraph.load(new File(req.modelPath()), true);
        return "ok";
    }
}
