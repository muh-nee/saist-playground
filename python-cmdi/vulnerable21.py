import subprocess

from flask import request


def run_diagnostic():
    host = request.args["host"]
    return subprocess.run(f"ping -c 1 {host}", shell=True, check=False)
