import onnxruntime as ort
from flask import Flask, request, jsonify
import numpy as np

app = Flask(__name__)
session = ort.InferenceSession("./models/classifier.onnx")

@app.route("/infer", methods=["POST"])
def infer():
    features = np.array(request.json.get("features"), dtype=np.float32).reshape(1, -1)
    result = session.run(None, {"input": features})
    return jsonify({"output": result[0].tolist()})
