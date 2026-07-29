import os


def get_db_password():
    return os.environ["DB_PASSWORD"]
