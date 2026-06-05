from langchain.agents import initialize_agent, AgentType, Tool
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o-mini")

PRODUCT_CATALOG = {"widget": 9.99, "gadget": 19.99, "sprocket": 4.50}


def lookup_price(product_name: str) -> str:
    name = product_name.strip().lower()
    if name not in PRODUCT_CATALOG:
        return "unknown product"
    return f"${PRODUCT_CATALOG[name]:.2f}"


def list_products(_: str) -> str:
    return ", ".join(sorted(PRODUCT_CATALOG.keys()))


tools = [
    Tool(name="lookup_price", func=lookup_price, description="Get price for a product name."),
    Tool(name="list_products", func=list_products, description="List all product names."),
]
agent = initialize_agent(tools, llm, agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION)


def handle_request(user_input):
    return agent.run(user_input)


if __name__ == "__main__":
    import sys
    print(handle_request(sys.argv[1]))
