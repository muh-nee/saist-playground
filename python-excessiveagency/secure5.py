from autogen import ConversableAgent, AssistantAgent
from autogen.coding import DockerCommandLineCodeExecutor

llm_config = {"config_list": [{"model": "gpt-4o", "api_key": "..."}]}

executor = DockerCommandLineCodeExecutor(
    image="python:3.12-slim",
    work_dir="/tmp/agent_sandbox",
    timeout=30,
)

code_executor_agent = ConversableAgent(
    name="code_executor",
    llm_config=False,
    code_execution_config={"executor": executor},
    human_input_mode="NEVER",
)

assistant = AssistantAgent(
    name="assistant",
    llm_config=llm_config,
)


if __name__ == "__main__":
    import sys
    code_executor_agent.initiate_chat(assistant, message=sys.argv[1])
