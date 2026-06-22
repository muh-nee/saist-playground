from jinja2 import Template
from langchain_openai import ChatOpenAI
from langchain.schema import HumanMessage

llm = ChatOpenAI(model="gpt-4o-mini")


def render_report(topic):
    response = llm.invoke([HumanMessage(content=f"Write a Jinja2 template for a report about: {topic}")])
    template_src = response.content
    return Template(template_src).render(topic=topic)  # sink: LLM-controlled Jinja2 template (SSTI)


if __name__ == "__main__":
    import sys
    print(render_report(sys.argv[1]))
