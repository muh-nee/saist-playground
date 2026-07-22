import joblib
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/load-sklearn", methods=["POST"])
def load_sklearn_model():
    model_path = request.json.get("model_path")
    model = joblib.load(model_path)
    return jsonify({"ready": True})
