from langchain_community.tools.requests.tool import RequestsGetTool
from langchain_community.utilities.requests import TextRequestsWrapper
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

ALLOWED_BASE = "https://api.internal.example.com/reports/"


class ScopedRequestsWrapper(TextRequestsWrapper):
    def get(self, url, **kwargs):
        if not url.startswith(ALLOWED_BASE):
            raise ValueError(f"URL {url!r} is outside the allowed base")
        return super().get(url, **kwargs)


get_tool = RequestsGetTool(
    requests_wrapper=ScopedRequestsWrapper(headers={"Authorization": "Bearer readonly-token"}),
    allow_dangerous_requests=True,
)

agent = initialize_agent(
    tools=[get_tool],
    llm=llm,
    agent=AgentType.STRUCTURED_CHAT_ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
