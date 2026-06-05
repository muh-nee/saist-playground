import sqlite3
from langchain_openai import ChatOpenAI
from langchain.schema import HumanMessage

llm = ChatOpenAI(model="gpt-4o-mini")
conn = sqlite3.connect("app.db")
cursor = conn.cursor()


def find_user_by_name(natural_language_query):
    response = llm.invoke([HumanMessage(content=f"Extract only the person's name from: {natural_language_query}")])
    name = response.content.strip()
    cursor.execute("SELECT * FROM users WHERE name = ?", (name,))
    return cursor.fetchall()


if __name__ == "__main__":
    import sys
    print(find_user_by_name(sys.argv[1]))
