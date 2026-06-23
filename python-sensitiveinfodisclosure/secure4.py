import hashlib
import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")


def analyze_user_activity(user_id):
    user = conn.execute(
        "SELECT email, name FROM users WHERE id = ?", (user_id,)
    ).fetchone()
    email_hash = hashlib.sha256(user[0].encode()).hexdigest()
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "user", "content": f"Analyze activity for user {email_hash[:8]} (ID: {user_id})."}
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    print(analyze_user_activity(1))
