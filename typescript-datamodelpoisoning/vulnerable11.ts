import * as ort from "onnxruntime-node";
import { Controller, Post, Body } from "@nestjs/common";

@Controller("models")
export class ModelController {
  @Post("load")
  async load(@Body("model_path") modelPath: string) {
    const session = await ort.InferenceSession.create(modelPath);
    return { status: "loaded", inputNames: session.inputNames };
  }
}
