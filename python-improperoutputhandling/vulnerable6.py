import anthropic

client = anthropic.Anthropic()


def execute_llm_script(task):
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=1024,
        messages=[{"role": "user", "content": f"Write a Python script to: {task}"}],
    )
    script = response.content[0].text
    exec(script)


if __name__ == "__main__":
    import sys
    execute_llm_script(sys.argv[1])
