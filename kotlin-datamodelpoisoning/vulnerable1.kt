package com.example.ml

import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ModelLoaderController {

    @PostMapping("/models/load")
    fun load(@RequestParam url: String): String {
        // sink: user-controlled URL fetched + loaded by DJL with no allowlist or hash check
        val criteria = Criteria.builder()
            .setTypes(ai.djl.ndarray.NDList::class.java, ai.djl.ndarray.NDList::class.java)
            .optModelUrls(url)
            .build()
        val model: ZooModel<ai.djl.ndarray.NDList, ai.djl.ndarray.NDList> = criteria.loadModel()
        return "loaded ${model.name}"
    }
}
