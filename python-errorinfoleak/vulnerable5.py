from flask import Flask, jsonify, request

app = Flask(__name__)


@app.errorhandler(Exception)
def global_error_handler(e):
    return jsonify({
        'error': str(e),
        'type': type(e).__name__,
        'args': e.args
    }), 500


@app.route('/data')
def data():
    raise ValueError("connection refused to postgres://user:pass@localhost/db")
