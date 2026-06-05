from flask import Flask, request
from markupsafe import Markup
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/bio")
def render_bio():
    name = request.args.get("name", "")
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": f"Write an HTML bio snippet for: {name}"}],
    )
    bio_html = Markup(response.choices[0].message.content)
    return f"<html><body><div class='bio'>{bio_html}</div></body></html>"
