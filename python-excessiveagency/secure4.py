import requests
from urllib.parse import urlparse
import anthropic

client = anthropic.Anthropic()

ALLOWED_HOSTS = {"api.internal.example.com", "data.internal.example.com"}


def fetch_internal_data(url: str) -> str:
    hostname = urlparse(url).hostname
    if hostname not in ALLOWED_HOSTS:
        raise ValueError(f"Host {hostname!r} is not in the allowed list")
    response = requests.get(url, timeout=10)
    return response.text


tools = [{
    "name": "fetch_internal_data",
    "description": "Fetch data from the internal reporting API",
    "input_schema": {
        "type": "object",
        "properties": {
            "url": {"type": "string"},
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
        if block.type == "tool_use" and block.name == "fetch_internal_data":
            return fetch_internal_data(block.input["url"])
    return response.content[0].text


if __name__ == "__main__":
    import sys
    print(run_agent(sys.argv[1]))
