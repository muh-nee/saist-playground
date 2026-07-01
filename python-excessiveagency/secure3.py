import subprocess
from langchain.tools import Tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

ALLOWED_COMMANDS = {"df", "free", "uptime", "hostname"}


def run_diagnostic(cmd: str) -> str:
    base = cmd.strip().split()[0]
    if base not in ALLOWED_COMMANDS:
        raise ValueError(f"Command not permitted: {base!r}")
    result = subprocess.run(cmd.split(), capture_output=True, text=True, timeout=10)
    return result.stdout


diagnostic_tool = Tool(
    name="RunDiagnostic",
    func=run_diagnostic,
    description="Run an approved system diagnostic command (df, free, uptime, hostname)",
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
