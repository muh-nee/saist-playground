import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")
cursor = conn.cursor()

ALLOWED_TABLES = {"users", "products", "orders"}


def query_table(user_question):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Reply with only the table name relevant to the question."},
            {"role": "user", "content": user_question},
        ],
    )
    table_name = response.choices[0].message.content.strip().lower()
    if table_name not in ALLOWED_TABLES:
        raise ValueError(f"Unknown table: {table_name}")
    cursor.execute(f"SELECT * FROM {table_name} WHERE active = ?", (1,))
    return cursor.fetchall()


if __name__ == "__main__":
    import sys
    print(query_table(sys.argv[1]))
