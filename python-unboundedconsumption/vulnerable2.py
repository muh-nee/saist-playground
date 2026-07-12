from langchain_openai import ChatOpenAI
from langchain.schema import HumanMessage

llm = ChatOpenAI(model="gpt-4o")

def summarize(text: str) -> str:
    response = llm.invoke([HumanMessage(content=f"Summarize: {text}")])
    return response.content
