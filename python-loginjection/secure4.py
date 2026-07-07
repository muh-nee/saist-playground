# Flask imports: from flask import Flask, request
# import logging

import logging
from flask import Flask, request

logger = logging.getLogger(__name__)
app = Flask(__name__)


@app.route("/login", methods=["POST"])
def login():
    username = request.form.get("username")
    # SAFE: stdlib lazy args form — the format string and value are passed as separate
    # arguments to the logging framework. The % formatting is evaluated INSIDE the framework,
    # not at the call site. This is the Python equivalent of SLF4J {} placeholders in Java.
    logger.info("login_attempt for user: %s", username)
    return "ok"
