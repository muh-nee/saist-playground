import boto3
from langchain.tools import tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)
s3 = boto3.client("s3")

ALLOWED_BUCKET = "app-reports"
ALLOWED_PREFIX = "summaries/"


@tool
def get_report_object(key: str) -> str:
    """Fetch a generated report object from the reports bucket."""
    if not key.startswith(ALLOWED_PREFIX):
        raise ValueError(f"Key {key!r} is outside the allowed prefix")
    obj = s3.get_object(Bucket=ALLOWED_BUCKET, Key=key)
    return obj["Body"].read().decode("utf-8")


agent = initialize_agent(
    tools=[get_report_object],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
