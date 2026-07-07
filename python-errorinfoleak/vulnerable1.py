from flask import Flask, jsonify, request
from sqlalchemy import create_engine, text

app = Flask(__name__)
engine = create_engine("postgresql://localhost/mydb")


@app.route('/user/<int:user_id>')
def get_user(user_id):
    try:
        with engine.connect() as conn:
            result = conn.execute(text("SELECT name FROM users WHERE id = :id"), {"id": user_id})
            row = result.fetchone()
            if row:
                return jsonify({"name": row[0]})
            return jsonify({"error": "not found"}), 404
    except Exception as e:
        return jsonify({'error': str(e)}), 500
