package com.example.ml

import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HuggingFaceController {

    // allowlist of (repo, pinned commit SHA) tuples maintained by security review
    private val allowed = mapOf(
        "sentence-transformers/all-MiniLM-L6-v2" to "e4ce9877abf3edfe10b0d82785e83bdcb973e22e",
        "sentence-transformers/paraphrase-MiniLM-L3-v2" to "8d6b950845285729817bf8e1af1861502c2fed0c",
    )

    @PostMapping("/models/hf")
    fun load(@RequestParam repo: String): String {
        val revision = allowed[repo] ?: throw SecurityException("repo not permitted: $repo")
        // safe: repo allowlisted and pinned to a specific commit SHA (not a mutable branch)
        val url = "https://huggingface.co/$repo/resolve/$revision/pytorch_model.bin"
        val criteria = Criteria.builder()
            .setTypes(ai.djl.ndarray.NDList::class.java, ai.djl.ndarray.NDList::class.java)
            .optModelUrls(url)
            .build()
        val model: ZooModel<ai.djl.ndarray.NDList, ai.djl.ndarray.NDList> = criteria.loadModel()
        return "loaded ${model.name}@$revision"
    }
}
