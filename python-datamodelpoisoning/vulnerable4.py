from flask import Flask, request, jsonify
from transformers import AutoModel

app = Flask(__name__)

@app.route("/load-model", methods=["POST"])
def load_model():
    model_name = request.json.get("model_name")
    model = AutoModel.from_pretrained(model_name)
    return jsonify({"loaded": True})
