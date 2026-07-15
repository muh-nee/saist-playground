from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain.schema import HumanMessage

embeddings = OpenAIEmbeddings()
memory_store = Chroma(collection_name="memory", embedding_function=embeddings)
llm = ChatOpenAI(model="gpt-4o", max_tokens=512)

def answer_and_remember(question: str) -> str:
    response = llm.invoke([HumanMessage(content=question)])
    answer = response.content
    memory_store.add_texts([answer])
    return answer
