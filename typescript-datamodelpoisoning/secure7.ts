import * as ort from "onnxruntime-node";
import { Controller, Post, Body, UseGuards } from "@nestjs/common";
import { AdminGuard } from "./guards/admin.guard";

@Controller("admin/models")
export class AdminModelController {
  @Post("load")
  @UseGuards(AdminGuard)
  async load(@Body("model_path") modelPath: string) {
    const session = await ort.InferenceSession.create(modelPath);
    return { status: "loaded", inputNames: session.inputNames };
  }
}
