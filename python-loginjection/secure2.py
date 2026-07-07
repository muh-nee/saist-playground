# import structlog

import structlog


def login(username):
    # Safe: structlog binds user data as a structured field; message string is fixed
    structlog.get_logger().bind(user=username).info("login_attempt")
