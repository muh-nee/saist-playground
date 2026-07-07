import numpy as np
from flask import Flask, request

app = Flask(__name__)

@app.route("/write")
def write_value():
    val = int(request.args.get("value", 0))
    if val > 2**31 - 1 or val < -(2**31):
        return "out of range", 400
    arr = np.zeros(10, dtype=np.int32)
    arr[0] = val
    return str(arr[0])
