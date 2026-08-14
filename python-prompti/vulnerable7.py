from flask import Flask, request
from anthropic import Anthropic
from mcp import ClientSession

app = Flask(__name__)
client = Anthropic()


@app.post("/agent")
def agent_turn():
    user_query = request.json["query"]
    messages = request.json.get("messages", [])

    with ClientSession() as mcp_session:
        tool_result = mcp_session.call_tool("web_search", {"query": user_query})
        mcp_output = tool_result.content[0].text

    messages.append({"role": "user", "content": mcp_output})
    response = client.messages.create(
        model="claude-opus-4-5",
        max_tokens=1024,
        messages=messages,
    )
    return {"reply": response.content[0].text}
