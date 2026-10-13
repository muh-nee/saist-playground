package com.example.ml

import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HuggingFaceController {

    @PostMapping("/models/hf")
    fun load(@RequestParam repo: String): String {
        // sink: HuggingFace model fetched with no pinned revision or integrity check
        val url = "https://huggingface.co/$repo/resolve/main/pytorch_model.bin"
        val criteria = Criteria.builder()
            .setTypes(ai.djl.ndarray.NDList::class.java, ai.djl.ndarray.NDList::class.java)
            .optModelUrls(url)
            .build()
        val model: ZooModel<ai.djl.ndarray.NDList, ai.djl.ndarray.NDList> = criteria.loadModel()
        return "loaded ${model.name} (unpinned)"
    }
}
