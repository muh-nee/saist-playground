import ctypes
from flask import Flask, request

app = Flask(__name__)

INT32_MAX = 2**31 - 1
INT32_MIN = -(2**31)

@app.route("/process")
def process():
    count = int(request.args.get("count", 0))
    if count > INT32_MAX or count < INT32_MIN:
        return "out of range", 400
    result = ctypes.c_int32(count).value
    return str(result)
