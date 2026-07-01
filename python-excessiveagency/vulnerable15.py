from langchain_community.agent_toolkits.openapi.toolkit import RequestsToolkit
from langchain_community.utilities.requests import TextRequestsWrapper
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

toolkit = RequestsToolkit(
    requests_wrapper=TextRequestsWrapper(
        headers={"Authorization": "Bearer admin-token"},
    ),
    allow_dangerous_requests=True,
)

agent = initialize_agent(
    tools=toolkit.get_tools(),
    llm=llm,
    agent=AgentType.STRUCTURED_CHAT_ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
