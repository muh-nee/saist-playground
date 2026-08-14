import chromadb
from openai import OpenAI

client = OpenAI()
chroma = chromadb.Client()
collection = chroma.get_or_create_collection("session_memory")


def summarize_and_store(user_query: str, session_id: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": user_query}]
    )
    llm_output = response.choices[0].message.content
    collection.add(documents=[llm_output], ids=[session_id])
    return "stored"
