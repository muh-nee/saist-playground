from langchain_openai import ChatOpenAI
from langchain.prompts import ChatPromptTemplate
from langchain.schema.runnable import RunnableSequence

llm = ChatOpenAI(model="gpt-4o-mini")
prompt = ChatPromptTemplate.from_messages([("user", "{input}")])
chain: RunnableSequence = prompt | llm

def run_chain(user_input: str) -> str:
    return chain.invoke({"input": user_input}).content
