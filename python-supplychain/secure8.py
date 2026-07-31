import numpy as np
import onnxruntime as ort
from flask import Flask, jsonify, request

app = Flask(__name__)
session = ort.InferenceSession("./models/classifier.onnx")

@app.route("/predict", methods=["POST"])
def predict():
    data = request.json.get("features")
    input_array = np.array(data, dtype=np.float32).reshape(1, -1)
    result = session.run(None, {"input": input_array})
    return jsonify({"prediction": result[0].tolist()})
