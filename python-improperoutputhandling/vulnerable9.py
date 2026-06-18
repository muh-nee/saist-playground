from jinja2 import Template
from openai import OpenAI

client = OpenAI()


def render_llm_response():
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": "Write a short HTML snippet about: the history of the internet"}],
    )
    llm_html = response.choices[0].message.content
    template = Template("<div class='content'>{{ content }}</div>")
    return template.render(content=llm_html)


if __name__ == "__main__":
    print(render_llm_response())
