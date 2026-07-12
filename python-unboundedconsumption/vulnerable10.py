from langchain_openai import ChatOpenAI
from langchain.schema import HumanMessage

llm = ChatOpenAI(model="gpt-4o", temperature=0.7)

def draft_reply(message: str) -> str:
    response = llm.invoke([HumanMessage(content=message)])
    return response.content
