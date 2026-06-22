import yaml
from flask import Flask, request
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/config")
def generate_config():
    description = request.args.get("description", "")
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Output a YAML configuration for the described deployment."},
            {"role": "user", "content": description},
        ],
    )
    raw = response.choices[0].message.content
    return str(yaml.unsafe_load(raw))  # sink: LLM-controlled YAML deserialized without SafeLoader
