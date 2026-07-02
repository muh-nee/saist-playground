import os
import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")


def debug_connection_error(error_msg):
    db_password = os.environ["DB_PASSWORD"]
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "user", "content": f"I'm getting this DB error: {error_msg}. The password I'm using is {db_password}. What's wrong?"}
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    print(debug_connection_error("connection refused"))


