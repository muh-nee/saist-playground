const tf = require("@tensorflow/tfjs-node");

const MODEL_URL = "file://./models/model.json";

async function loadModel() {
  return tf.loadLayersModel(MODEL_URL);
}

loadModel();
