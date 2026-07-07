# Flask imports: from flask import Flask, request
# import logging

import logging

logger = logging.getLogger(__name__)


def handle_error(user_id, e):
    # Vulnerable: user_id concatenated with exception string into log message
    logger.error("Exception for user: " + user_id + ": " + str(e))
