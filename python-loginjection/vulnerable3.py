# Flask imports: from flask import Flask, request
# import logging

import logging

logger = logging.getLogger(__name__)


def process(username):
    # Vulnerable: % format operator embeds user data into the log message string
    logger.error("Failed for user: %s" % username)
