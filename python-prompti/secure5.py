from anthropic import Anthropic

STATIC_SYSTEM_PROMPT = "You are a helpful search assistant."

client = Anthropic()


def agent_turn(messages: list, mcp_result: dict) -> str:
    if not isinstance(mcp_result.get("result_count"), int):
        raise ValueError("Unexpected MCP tool output format")

    safe_content = f"Found {mcp_result['result_count']} results"

    messages.append({"role": "user", "content": safe_content})

    response = client.messages.create(
        model="claude-opus-4-5",
        max_tokens=1024,
        system=STATIC_SYSTEM_PROMPT,
        messages=messages,
    )
    return response.content[0].text
