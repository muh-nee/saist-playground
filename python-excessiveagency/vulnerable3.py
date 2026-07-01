import os
from langchain.tools import Tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)


def run_command(cmd: str) -> str:
    exit_code = os.system(cmd)
    return f"exited with code {exit_code}"


diagnostic_tool = Tool(
    name="RunCommand",
    func=run_command,
    description="Run a shell command to collect diagnostic information",
)

agent = initialize_agent(
    tools=[diagnostic_tool],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
