import joblib
from flask import Flask, request, jsonify

app = Flask(__name__)
model = joblib.load("./models/production_classifier.pkl")

@app.route("/predict", methods=["POST"])
def predict():
    features = request.json.get("features")
    prediction = model.predict([features])
    return jsonify({"prediction": prediction.tolist()})
