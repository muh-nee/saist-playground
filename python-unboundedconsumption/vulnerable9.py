from llama_index.llms.openai import OpenAI
from llama_index.core import Settings, VectorStoreIndex

Settings.llm = OpenAI(model="gpt-4o")

def query_docs(index: VectorStoreIndex, question: str) -> str:
    query_engine = index.as_query_engine()
    return str(query_engine.query(question))
