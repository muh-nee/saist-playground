from flask import request


def find_user(cursor):
    email = request.args["email"]
    query = f"SELECT * FROM users WHERE email = '{email}'"
    return cursor.execute(query).fetchall()
