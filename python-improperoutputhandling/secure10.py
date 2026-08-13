import re
from flask import Flask, jsonify, make_response
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/summary")
def get_summary():
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        max_tokens=512,
        messages=[{"role": "user", "content": "Summarize the latest AI news in Markdown."}],
    )
    content = response.choices[0].message.content
    sanitized = re.sub(r'!\[[^\]]*\]\([^)]*\)', '', content)
    sanitized = re.sub(r'!\[[^\]]*\]\[[^\]]*\]', '', sanitized)
    sanitized = re.sub(r'<img\b[^>]*/?>\s*', '', sanitized, flags=re.IGNORECASE)
    return make_response(jsonify({"content": sanitized}))
