# Flask imports: from flask import Flask, request
# import logging

import logging


def log_user(username):
    # Vulnerable: user input passed directly as the entire log message string
    logging.info(username)
