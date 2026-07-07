# Flask imports: from flask import Flask, request
# import logging

import logging

logger = logging.getLogger(__name__)


def login(username):
    # Safe: fixed message string; user data is passed via extra={} dict, not embedded in message
    logger.info("login_attempt", extra={"user": username})
