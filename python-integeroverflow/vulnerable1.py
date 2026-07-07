import ctypes
from flask import Flask, request

app = Flask(__name__)

@app.route("/process")
def process():
    count = int(request.args.get("count", 0))
    result = ctypes.c_int32(count).value  # silently truncates if count > 2^31-1
    return str(result)
