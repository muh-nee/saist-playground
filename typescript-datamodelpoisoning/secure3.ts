import * as tf from "@tensorflow/tfjs-node";

const MODEL_URL = "file://./models/model.json";

async function loadModel(): Promise<tf.LayersModel> {
  return tf.loadLayersModel(MODEL_URL);
}

loadModel();
