import os
from langchain.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o")
prompt = ChatPromptTemplate.from_template("{input}")
chain = prompt | llm


def debug_api_failure(service_name):
    api_key = os.getenv("PAYMENT_API_KEY")
    result = chain.invoke({
        "input": f"I'm getting auth failures on {service_name}. My API key is {api_key}. What could be wrong?"
    })
    return result.content


if __name__ == "__main__":
    print(debug_api_failure("payments-service"))
