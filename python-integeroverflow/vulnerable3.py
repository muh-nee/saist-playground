import ctypes
from flask import Flask, request

app = Flask(__name__)

@app.route("/compute")
def compute():
    a = int(request.args.get("a", 0))
    b = int(request.args.get("b", 0))
    product = a * b  # Python int: no overflow here
    truncated = ctypes.c_uint32(product).value  # wraps silently to uint32
    return str(truncated)
