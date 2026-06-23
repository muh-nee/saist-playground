import os
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain
from langchain_openai import OpenAI

llm = OpenAI()
prompt = PromptTemplate(input_variables=["input"], template="{input}")
chain = LLMChain(llm=llm, prompt=prompt)


def investigate_oauth_failure(provider):
    oauth_secret = os.environ["OAUTH_CLIENT_SECRET"]
    result = chain.run(
        input=f"Investigate OAuth failure for {provider}. Client secret in use: {oauth_secret}"
    )
    return result


if __name__ == "__main__":
    print(investigate_oauth_failure("github"))
