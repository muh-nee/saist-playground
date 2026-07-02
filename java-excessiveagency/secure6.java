package main;

import dev.langchain4j.agent.tool.Tool;
import io.fabric8.kubernetes.client.KubernetesClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SafeClusterTools {
    record PodDeletion(String namespace, String podName) {}

    private final KubernetesClient k8s;
    private final Map<String, PodDeletion> pending = new ConcurrentHashMap<>();

    SafeClusterTools(KubernetesClient k8s) {
        this.k8s = k8s;
    }

    @Tool("Propose a pod deletion — requires human approval before it is executed")
    public String requestPodDeletion(String namespace, String podName) {
        String ticketId = "delpod-" + namespace + "-" + podName;
        pending.put(ticketId, new PodDeletion(namespace, podName));
        return ticketId;
    }

    public void approve(String ticketId, boolean humanApproved) {
        if (!humanApproved) throw new IllegalStateException("not approved");
        PodDeletion req = pending.remove(ticketId);
        if (req == null) throw new IllegalArgumentException("unknown ticket");
        k8s.pods().inNamespace(req.namespace()).withName(req.podName()).delete();
    }
}
