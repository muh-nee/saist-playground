import sqlite3
from llama_index.core import VectorStoreIndex, Document
from llama_index.llms.openai import OpenAI

conn = sqlite3.connect("app.db")
llm = OpenAI(model="gpt-4o")
index = VectorStoreIndex.from_documents([Document(text="User activity records.")])


def summarize_for_support(user_id):
    user = conn.execute(
        "SELECT name, email, credit_card FROM users WHERE id = ?", (user_id,)
    ).fetchone()
    query_engine = index.as_query_engine(llm=llm)
    response = query_engine.query(
        f"Summarize account activity for {user[0]} (email: {user[1]}, card: {user[2]})"
    )
    return str(response)


if __name__ == "__main__":
    print(summarize_for_support(1))
