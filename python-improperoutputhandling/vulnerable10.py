from flask import Flask
from markupsafe import Markup
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/bio")
def render_bio():
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": "Write an HTML bio snippet for: Ada Lovelace"}],
    )
    bio_html = Markup(response.choices[0].message.content)
    return f"<html><body><div class='bio'>{bio_html}</div></body></html>"
