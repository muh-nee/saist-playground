import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")


def summarize_user_activity(user_id):
    user = conn.execute(
        "SELECT name, email, ssn FROM users WHERE id = ?", (user_id,)
    ).fetchone()
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "user", "content": f"Summarize recent activity for {user[0]} (email: {user[1]}, SSN: {user[2]})."}
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    print(summarize_user_activity(1))
