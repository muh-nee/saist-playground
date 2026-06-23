import sqlite3
from langchain.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI

conn = sqlite3.connect("app.db")
llm = ChatOpenAI(model="gpt-4o-mini")
prompt = ChatPromptTemplate.from_template(
    "Summarize the following product description in one sentence: {description}"
)
chain = prompt | llm


def summarize_product(product_id):
    product = conn.execute(
        "SELECT name, description FROM products WHERE id = ?", (product_id,)
    ).fetchone()
    result = chain.invoke({"description": product[1]})
    return result.content


if __name__ == "__main__":
    print(summarize_product(1))
