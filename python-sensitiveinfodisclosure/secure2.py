import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")


def summarize_user_activity(user_id):
    row = conn.execute(
        "SELECT last_login, login_count FROM users WHERE id = ?", (user_id,)
    ).fetchone()
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "user", "content": f"Summarize activity for user {user_id}: last login {row[0]}, total logins {row[1]}."}
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    print(summarize_user_activity(1))
