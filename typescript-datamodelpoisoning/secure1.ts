import * as ort from "onnxruntime-node";

const MODEL_PATH = "./models/classifier.onnx";

let session: ort.InferenceSession;

async function initSession(): Promise<void> {
  session = await ort.InferenceSession.create(MODEL_PATH);
}

initSession();

