import sqlite3
from llama_index.core import VectorStoreIndex, Document
from llama_index.llms.openai import OpenAI

conn = sqlite3.connect("app.db")
llm = OpenAI(model="gpt-4o")
index = VectorStoreIndex.from_documents([Document(text="Internal customer support knowledge base.")])


def assist_customer(user_id, question):
    user = conn.execute(
        "SELECT name, email, phone FROM users WHERE id = ?", (user_id,)
    ).fetchone()
    chat_engine = index.as_chat_engine(llm=llm)
    response = chat_engine.chat(
        f"Customer {user[0]} (email: {user[1]}, phone: {user[2]}) asks: {question}"
    )
    return str(response)


if __name__ == "__main__":
    print(assist_customer(1, "how do I reset my settings"))
