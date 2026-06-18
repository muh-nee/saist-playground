import subprocess
from langchain_openai import ChatOpenAI
from langchain.schema import HumanMessage

llm = ChatOpenAI(model="gpt-4o-mini")


def run_llm_command():
    response = llm.invoke([HumanMessage(content="Give me a shell command to list all running processes")])
    command = response.content
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    return result.stdout


if __name__ == "__main__":
    print(run_llm_command())
