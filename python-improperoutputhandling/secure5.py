from flask import Flask, request
from markupsafe import escape
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/summarize")
def summarize():
    text = request.args.get("text", "")
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        max_tokens=1024,
        messages=[{"role": "user", "content": f"Summarize: {text}"}],
    )
    summary = escape(response.choices[0].message.content)
    return f"<html><body><p>Note: AI-generated content. Verify independently.<br>{summary}</p></body></html>"
