import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")
cursor = conn.cursor()


def search_users(user_request):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Extract only the search term from the user request. Output plain text only."},
            {"role": "user", "content": user_request},
        ],
    )
    search_term = response.choices[0].message.content.strip()
    cursor.execute("SELECT * FROM users WHERE name = ?", (search_term,))
    return cursor.fetchall()


if __name__ == "__main__":
    import sys
    print(search_users(sys.argv[1]))
