import json
from openai import OpenAI

client = OpenAI()


def read_file(path: str) -> str:
    with open(path) as f:
        return f.read()


def handle_request(user_query: str) -> str:
    messages = [{"role": "user", "content": user_query}]

    response = client.chat.completions.create(
        model="gpt-4o",
        messages=messages,
        tools=[{
            "type": "function",
            "function": {
                "name": "read_file",
                "description": "Read a file from disk and return its contents",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "path": {"type": "string"}
                    },
                    "required": ["path"],
                },
            },
        }],
    )

    tool_call = response.choices[0].message.tool_calls[0]
    args = json.loads(tool_call.function.arguments)
    return read_file(args["path"])


if __name__ == "__main__":
    import sys
    print(handle_request(sys.argv[1]))
