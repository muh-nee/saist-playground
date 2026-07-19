from flask import Flask, request, jsonify
from transformers import AutoConfig, AutoModel

app = Flask(__name__)

@app.route("/load-config", methods=["POST"])
def load_config():
    model_name = request.json.get("model_name")
    config = AutoConfig.from_pretrained(model_name)
    model = AutoModel.from_config(config)
    return jsonify({"loaded": True})
