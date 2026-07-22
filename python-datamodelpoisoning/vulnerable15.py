import pickle
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/load-model", methods=["POST"])
def load_model():
    model_path = request.json.get("model_path")
    with open(model_path, "rb") as f:
        model = pickle.load(f)
    return jsonify({"loaded": True})
