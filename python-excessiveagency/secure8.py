import requests
import json
from openai import OpenAI

client = OpenAI()


def lookup_quote(symbol: str) -> dict:
    return requests.get(
        "https://api.example.com/quote",
        params={"s": symbol},
        timeout=10,
    ).json()


def handle_request(user_query: str) -> str:
    messages = [{"role": "user", "content": user_query}]
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=messages,
        tools=[{
            "type": "function",
            "function": {
                "name": "lookup_quote",
                "description": "Look up a stock quote by ticker symbol",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "symbol": {"type": "string"},
                    },
                    "required": ["symbol"],
                },
            },
        }],
    )
    tool_call = response.choices[0].message.tool_calls[0]
    args = json.loads(tool_call.function.arguments)
    return str(lookup_quote(args["symbol"]))


if __name__ == "__main__":
    import sys
    print(handle_request(sys.argv[1]))
