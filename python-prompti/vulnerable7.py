from anthropic import Anthropic

client = Anthropic()


def agent_turn(messages: list, mcp_tool_output: str) -> str:
    messages.append({"role": "user", "content": mcp_tool_output})
    response = client.messages.create(
        model="claude-opus-4-5",
        max_tokens=1024,
        messages=messages,
    )
    return response.content[0].text
