import sqlite3
from langchain.tools import Tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)
conn = sqlite3.connect("orders.db")


def query_db(sql: str) -> str:
    cursor = conn.execute(sql)
    rows = cursor.fetchall()
    return str(rows)


db_tool = Tool(
    name="QueryDatabase",
    func=query_db,
    description="Query the orders database to answer questions about customer orders",
)

agent = initialize_agent(
    tools=[db_tool],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
