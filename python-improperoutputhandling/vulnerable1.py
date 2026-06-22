import yaml
from openai import OpenAI

client = OpenAI()


def load_service_config(description):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Output a YAML configuration object for the described service."},
            {"role": "user", "content": description},
        ],
    )
    raw = response.choices[0].message.content
    return yaml.unsafe_load(raw)  # sink: LLM-controlled YAML deserialized without SafeLoader


if __name__ == "__main__":
    import sys
    print(load_service_config(sys.argv[1]))
