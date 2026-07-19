import joblib
from flask import Flask, request, jsonify

app = Flask(__name__)
model = joblib.load("./models/classifier.pkl")

@app.route("/predict", methods=["POST"])
def predict():
    X = request.json.get("features")
    result = model.predict([X])
    return jsonify({"class": int(result[0])})
