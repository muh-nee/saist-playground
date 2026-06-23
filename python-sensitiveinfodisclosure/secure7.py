import sqlite3
from llama_index.core import VectorStoreIndex, Document
from llama_index.llms.openai import OpenAI

conn = sqlite3.connect("app.db")
llm = OpenAI(model="gpt-4o")
index = VectorStoreIndex.from_documents([Document(text="Product documentation and FAQs.")])


def get_product_info(product_id):
    product = conn.execute(
        "SELECT name, category FROM products WHERE id = ?", (product_id,)
    ).fetchone()
    query_engine = index.as_query_engine(llm=llm)
    response = query_engine.query(
        f"What information is available about {product[0]} in the {product[1]} category?"
    )
    return str(response)


if __name__ == "__main__":
    print(get_product_info(1))
