import os
from langchain_openai import ChatOpenAI
from langchain.chains import LLMChain
from langchain.prompts import PromptTemplate

llm = ChatOpenAI(model="gpt-4o-mini")
prompt = PromptTemplate(input_variables=["task"], template="Give the shell command to {task}. Output only the command.")
chain = LLMChain(llm=llm, prompt=prompt)


def run_task(task):
    command = chain.run(task)
    stream = os.popen(command)
    return stream.read()


if __name__ == "__main__":
    import sys
    print(run_task(sys.argv[1]))
