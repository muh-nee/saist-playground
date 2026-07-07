import traceback
from flask import Flask, jsonify, request

app = Flask(__name__)


@app.route('/process')
def process():
    try:
        input_data = request.get_json()
        result = run_processing(input_data)
        return jsonify(result)
    except Exception:
        return jsonify({'error': traceback.format_exc()}), 500


def run_processing(data):
    return data
