from llama_index.core import VectorStoreIndex, Document
from llama_index.llms.openai import OpenAI

llm = OpenAI(model="gpt-4o")
index = VectorStoreIndex.from_documents([Document(text="Public FAQ and product help articles.")])


def general_help_chat(topic):
    chat_engine = index.as_chat_engine(llm=llm)
    response = chat_engine.chat(f"What can you tell me about {topic}?")
    return str(response)


if __name__ == "__main__":
    print(general_help_chat("subscription tiers"))
