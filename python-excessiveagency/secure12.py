from typing import Literal
from langchain.tools import tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

SECTION_PATHS = {
    "summary": "/var/app/reports/summary.txt",
    "details": "/var/app/reports/details.txt",
    "appendix": "/var/app/reports/appendix.txt",
}


@tool
def read_report_section(section: Literal["summary", "details", "appendix"]) -> str:
    """Read a specific section of the quarterly report."""
    with open(SECTION_PATHS[section]) as f:
        return f.read()


agent = initialize_agent(
    tools=[read_report_section],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
