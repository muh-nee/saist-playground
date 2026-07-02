package main;

import dev.langchain4j.agent.tool.Tool;
import io.fabric8.kubernetes.client.KubernetesClient;

class ClusterTools {
    private final KubernetesClient k8s;

    ClusterTools(KubernetesClient k8s) {
        this.k8s = k8s;
    }

    @Tool("Delete a pod in a namespace")
    public String deletePod(String namespace, String podName) {
        k8s.pods().inNamespace(namespace).withName(podName).delete();
        return "deleted " + namespace + "/" + podName;
    }
}
