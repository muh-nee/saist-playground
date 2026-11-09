from flask import Flask, request
import sqlite3

app = Flask(__name__)

@app.get('/users')
def users():
    name = request.args['name']
    with sqlite3.connect('users.db') as db:
        query = "SELECT * FROM users WHERE name = '" + name + "'"
        return str(db.execute(query).fetchall())
