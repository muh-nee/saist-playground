from langchain.agents import initialize_agent, AgentType
from langchain_community.tools import ShellTool
from langchain_experimental.tools import PythonREPLTool
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o-mini")
tools = [ShellTool(), PythonREPLTool()]
agent = initialize_agent(tools, llm, agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION)


def handle_request(user_input):
    return agent.run(user_input)


if __name__ == "__main__":
    import sys
    print(handle_request(sys.argv[1]))
