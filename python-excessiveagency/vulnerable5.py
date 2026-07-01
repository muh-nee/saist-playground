import requests
import anthropic

client = anthropic.Anthropic()


def fetch_url(url: str) -> str:
    response = requests.get(url, timeout=10)
    return response.text


tools = [{
    "name": "fetch_url",
    "description": "Fetch the content of a URL and return the response body",
    "input_schema": {
        "type": "object",
        "properties": {
            "url": {"type": "string"}
        },
        "required": ["url"],
    },
}]


def run_agent(user_query: str) -> str:
    messages = [{"role": "user", "content": user_query}]
    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=1024,
        tools=tools,
        messages=messages,
    )

    for block in response.content:
        if block.type == "tool_use" and block.name == "fetch_url":
            return fetch_url(block.input["url"])

    return response.content[0].text


if __name__ == "__main__":
    import sys
    print(run_agent(sys.argv[1]))
