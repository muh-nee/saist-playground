import ctypes
from flask import Flask, request

app = Flask(__name__)

@app.route("/id")
def get_id():
    user_id = int(request.args.get("id", 0))
    # c_int16 truncates values beyond 16-bit range silently
    short_id = ctypes.c_int16(user_id).value
    return str(short_id)
