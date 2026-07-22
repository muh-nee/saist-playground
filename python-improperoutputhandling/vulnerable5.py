from flask import Flask, render_template_string
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

DOCUMENT = "The quick brown fox jumps over the lazy dog. This is a sample document for summarization."


@app.route("/summarize")
def summarize():
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        max_tokens=1024,
        messages=[{"role": "user", "content": f"Summarize this: {DOCUMENT}"}],
    )
    summary = "Note: AI-generated content. Verify independently.\n\n" + response.choices[0].message.content
    return render_template_string(f"<html><body><p>{summary}</p></body></html>")
