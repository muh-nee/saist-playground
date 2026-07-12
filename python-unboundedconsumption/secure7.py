from llama_index.llms.openai import OpenAI
from llama_index.core import Settings

Settings.llm = OpenAI(model="gpt-4o", max_tokens=512)

def query_docs(query_engine, question: str) -> str:
    return str(query_engine.query(question))
