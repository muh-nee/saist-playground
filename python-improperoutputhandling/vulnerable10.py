from jinja2 import Template
from openai import OpenAI

client = OpenAI()


def generate_email(context):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Write a Jinja2 email template for the described scenario. Output only the template string."},
            {"role": "user", "content": context},
        ],
    )
    template_src = response.choices[0].message.content
    return Template(template_src).render(**context)  # sink: LLM-controlled Jinja2 template (SSTI)


if __name__ == "__main__":
    import sys
    print(generate_email(sys.argv[1]))
