# Flask imports: from flask import Flask, request
# import logging

import logging


def login(username):
    # Safe: CRLF characters stripped from user input before embedding in log message
    sanitized = username.replace("\r", "").replace("\n", "")
    logging.info("Login attempt for: " + sanitized)
