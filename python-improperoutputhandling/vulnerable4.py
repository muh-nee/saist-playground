import subprocess
from langchain_openai import ChatOpenAI
from langchain.schema import HumanMessage

llm = ChatOpenAI(model="gpt-4o-mini")


def run_llm_command(user_input):
    response = llm.invoke([HumanMessage(content=f"Give me a shell command to {user_input}")])
    command = response.content
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    return result.stdout


if __name__ == "__main__":
    import sys
    print(run_llm_command(sys.argv[1]))
