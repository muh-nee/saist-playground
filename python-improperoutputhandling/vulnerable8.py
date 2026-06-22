import yaml
from langchain_openai import ChatOpenAI
from langchain.schema import SystemMessage, HumanMessage

llm = ChatOpenAI(model="gpt-4o-mini")


def apply_policy(policy_description):
    response = llm.invoke([
        SystemMessage(content="Output a YAML policy document for the described access control rule."),
        HumanMessage(content=policy_description),
    ])
    return yaml.unsafe_load(response.content)  # sink: LLM-controlled YAML deserialized without SafeLoader


if __name__ == "__main__":
    import sys
    print(apply_policy(sys.argv[1]))
