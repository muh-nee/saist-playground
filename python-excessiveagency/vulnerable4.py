from langchain_community.agent_toolkits import FileManagementToolkit
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

toolkit = FileManagementToolkit()
tools = toolkit.get_tools()

agent = initialize_agent(
    tools=tools,
    llm=llm,
    agent=AgentType.STRUCTURED_CHAT_ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
