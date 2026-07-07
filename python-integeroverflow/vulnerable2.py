import numpy as np
from flask import Flask, request

app = Flask(__name__)

@app.route("/write")
def write_value():
    val = int(request.args.get("value", 0))
    arr = np.zeros(10, dtype=np.int32)
    arr[0] = val  # silently wraps if val exceeds int32 range
    return str(arr[0])
