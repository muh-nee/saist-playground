from flask import Flask, request

app = Flask(__name__)

@app.route("/compute")
def compute():
    a = int(request.args.get("a", 0))
    b = int(request.args.get("b", 0))
    # Python int arithmetic is arbitrary precision — no overflow
    result = a * b
    return str(result)
