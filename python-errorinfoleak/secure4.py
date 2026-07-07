import logging
from flask import Flask, jsonify

app = Flask(__name__)
logger = logging.getLogger(__name__)


@app.errorhandler(Exception)
def global_error_handler(e):
    logger.exception('Unhandled exception: %s', type(e).__name__)
    return jsonify({'error': 'internal server error'}), 500


@app.route('/data')
def data():
    return jsonify({'value': 42})
