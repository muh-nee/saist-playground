from jinja2 import Template
from openai import OpenAI

client = OpenAI()


def render_llm_response(user_topic):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": f"Write a short HTML snippet about: {user_topic}"}],
    )
    llm_html = response.choices[0].message.content
    template = Template("<div class='content'>{{ content }}</div>")
    return template.render(content=llm_html)


if __name__ == "__main__":
    import sys
    print(render_llm_response(sys.argv[1]))
