import pickle
from flask import Flask, request, jsonify

app = Flask(__name__)

with open("./models/classifier.pkl", "rb") as f:
    model = pickle.load(f)

@app.route("/predict", methods=["POST"])
def predict():
    X = request.json.get("features")
    result = model.predict([X])
    return jsonify({"class": int(result[0])})
