from flask import Flask, request

app = Flask(__name__)


@app.get("/error")
def error_page():
    return f"<html><body>{request.args.get('message')}</body></html>"
