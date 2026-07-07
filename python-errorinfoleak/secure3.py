from flask import Flask, jsonify

app = Flask(__name__)


class AppError(Exception):
    def __init__(self, internal_msg, user_msg='internal server error'):
        super().__init__(internal_msg)
        self.user_msg = user_msg


@app.errorhandler(AppError)
def handle_app_error(e):
    app.logger.exception('app error occurred')
    return jsonify({'error': e.user_msg}), 500


@app.route('/data')
def data():
    raise AppError('connection refused to postgres://user:pass@localhost/db')
