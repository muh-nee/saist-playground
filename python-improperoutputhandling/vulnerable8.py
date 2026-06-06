from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o-mini")


def run_diagnostic(user_request):
    command = llm.invoke(f"Output only the shell command to run: {user_request}")
    output = os.popen(command.content)  # sink: LLM-generated shell command executed directly
    return output.read()


if __name__ == "__main__":
    import os
    import sys
    print(run_diagnostic(sys.argv[1]))
