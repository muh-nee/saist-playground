import pickle
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/restore", methods=["POST"])
def restore_model():
    model_bytes = request.data
    model = pickle.loads(model_bytes)
    return jsonify({"restored": True})
