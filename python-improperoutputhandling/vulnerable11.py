import yaml
from flask import Flask, request
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()


@app.route("/provision")
def provision():
    description = request.args.get("description", "")
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Output a YAML manifest for provisioning the described cloud resource."},
            {"role": "user", "content": description},
        ],
    )
    raw = response.choices[0].message.content
    config = yaml.unsafe_load(raw)  # sink: LLM-controlled YAML deserialized without SafeLoader
    return str(config)
