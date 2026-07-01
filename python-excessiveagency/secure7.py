import sqlite3
from langchain.tools import tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)
conn = sqlite3.connect("orders.db")


@tool
def query_orders(order_id: int) -> str:
    """Look up an order by ID."""
    cursor = conn.execute("SELECT * FROM orders WHERE id = ?", (order_id,))
    row = cursor.fetchone()
    return str(row)


agent = initialize_agent(
    tools=[query_orders],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
