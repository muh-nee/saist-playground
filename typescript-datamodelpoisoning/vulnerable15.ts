import * as brain from "brain.js";
import { Controller, Post, Body } from "@nestjs/common";

@Controller("train")
export class TrainController {
  private readonly net = new brain.NeuralNetwork();

  @Post("from-url")
  async trainFromUrl(@Body("dataset_url") datasetUrl: string) {
    const response = await fetch(datasetUrl);
    const trainingData = await response.json();
    await this.net.trainAsync(trainingData);
    return { status: "trained" };
  }
}
