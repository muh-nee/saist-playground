import boto3
from langchain.tools import tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)
s3 = boto3.client("s3")
iam = boto3.client("iam")


@tool
def delete_s3_object(bucket: str, key: str) -> str:
    """Delete an object from S3."""
    s3.delete_object(Bucket=bucket, Key=key)
    return f"deleted s3://{bucket}/{key}"


@tool
def attach_role_policy(role_name: str, policy_arn: str) -> str:
    """Attach an IAM managed policy to a role."""
    iam.attach_role_policy(RoleName=role_name, PolicyArn=policy_arn)
    return f"attached {policy_arn} to {role_name}"


agent = initialize_agent(
    tools=[delete_s3_object, attach_role_policy],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
