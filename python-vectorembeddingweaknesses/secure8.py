from llama_index.core import VectorStoreIndex, SimpleDirectoryReader

documents = SimpleDirectoryReader("./internal_docs").load_data()
index = VectorStoreIndex.from_documents(documents)

def query_knowledge_base(question: str) -> str:
    query_engine = index.as_query_engine()
    return str(query_engine.query(question))
