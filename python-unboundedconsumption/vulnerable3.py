from langchain_anthropic import ChatAnthropic
from langchain.schema import HumanMessage

llm = ChatAnthropic(model="claude-3-5-sonnet-20241022")

def classify(text: str) -> str:
    response = llm.invoke([HumanMessage(content=f"Classify: {text}")])
    return response.content
