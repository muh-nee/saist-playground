import re
import chromadb
from openai import OpenAI

client = OpenAI()
chroma = chromadb.Client()
collection = chroma.get_or_create_collection("session_memory")


def _sanitize(text: str) -> str:
    text = re.sub(r"(?i)(ignore (all |previous )?instructions?|you are now|system:)", "", text)
    text = re.sub(r"<\|[^|]*\|>", "", text)
    return text.strip()


def summarize_and_store(user_query: str, session_id: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": user_query}]
    )
    llm_output = response.choices[0].message.content
    sanitized = _sanitize(llm_output)
    collection.add(documents=[sanitized], ids=[session_id])
    return "stored"
