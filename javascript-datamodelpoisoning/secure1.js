const ort = require("onnxruntime-node");

const MODEL_PATH = "./models/classifier.onnx";

let session;

async function initSession() {
  session = await ort.InferenceSession.create(MODEL_PATH);
}

initSession();
