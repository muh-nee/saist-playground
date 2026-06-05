from flask import Flask, request, render_template_string
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/summarize")
def summarize():
    text = request.args.get("text", "")
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": f"Summarize this: {text}"}],
    )
    summary = response.choices[0].message.content
    return render_template_string(f"<html><body><p>{summary}</p></body></html>")
