from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o-mini")


def run_diagnostic():
    command = llm.invoke("Output only the shell command to run: list all running processes")
    output = os.popen(command.content)  # sink: LLM-generated shell command executed directly
    return output.read()


if __name__ == "__main__":
    import os
    print(run_diagnostic())
