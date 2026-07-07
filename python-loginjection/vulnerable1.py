# Flask imports: from flask import Flask, request
# import logging

import logging

logger = logging.getLogger(__name__)


def login(username):
    # Vulnerable: user-controlled data concatenated directly into log message string
    logging.info("Login attempt for user: " + username)
