from flask import Flask, request, jsonify
from sklearn.linear_model import SGDClassifier
import numpy as np

app = Flask(__name__)
classifier = SGDClassifier()

@app.route("/update-model", methods=["POST"])
def update_model():
    X_raw = request.json.get("features")
    y_raw = request.json.get("labels")
    X = np.array(X_raw)
    y = np.array(y_raw)
    classifier.partial_fit(X, y)
    return jsonify({"updated": True})
