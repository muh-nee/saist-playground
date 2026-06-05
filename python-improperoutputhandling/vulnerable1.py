import sqlite3
from openai import OpenAI

client = OpenAI()
conn = sqlite3.connect("app.db")
cursor = conn.cursor()


def run_query_from_llm(user_question):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Translate the user's question into a SQL query."},
            {"role": "user", "content": user_question},
        ],
    )
    sql = response.choices[0].message.content
    cursor.execute(sql)
    return cursor.fetchall()


if __name__ == "__main__":
    import sys
    print(run_query_from_llm(sys.argv[1]))
