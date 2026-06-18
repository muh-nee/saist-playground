import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")
cursor = conn.cursor()

ALLOWED_QUERIES = {
    "users": "SELECT * FROM users WHERE active = ?",
    "products": "SELECT * FROM products WHERE active = ?",
    "orders": "SELECT * FROM orders WHERE active = ?",
}


def query_table(user_question):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Reply with only the table name relevant to the question."},
            {"role": "user", "content": user_question},
        ],
    )
    table_name = response.choices[0].message.content.strip().lower()
    query = ALLOWED_QUERIES.get(table_name)
    if query is None:
        raise ValueError(f"Unknown table: {table_name}")
    cursor.execute(query, (1,))
    return cursor.fetchall()


if __name__ == "__main__":
    import sys
    print(query_table(sys.argv[1]))
