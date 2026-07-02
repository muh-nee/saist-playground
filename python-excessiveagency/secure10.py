from langchain.tools import tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

SUPPORTED_TIMEZONES = {"UTC", "America/New_York", "Europe/London", "Asia/Tokyo"}


@tool
def current_time(timezone: str) -> str:
    """Return the current time in one of the supported timezones."""
    if timezone not in SUPPORTED_TIMEZONES:
        raise ValueError(f"Unsupported timezone: {timezone!r}")
    from datetime import datetime
    from zoneinfo import ZoneInfo
    return datetime.now(ZoneInfo(timezone)).isoformat()


agent = initialize_agent(
    tools=[current_time],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
