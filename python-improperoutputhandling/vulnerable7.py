from sqlalchemy import create_engine, text
from openai import OpenAI

client = OpenAI()
engine = create_engine("sqlite:///app.db")


def query_from_natural_language(question):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Convert natural language to SQL for a SQLite database with a 'users' table."},
            {"role": "user", "content": question},
        ],
    )
    sql = response.choices[0].message.content.strip()
    with engine.connect() as conn:
        result = conn.execute(text(sql))
        return result.fetchall()


if __name__ == "__main__":
    import sys
    print(query_from_natural_language(sys.argv[1]))
