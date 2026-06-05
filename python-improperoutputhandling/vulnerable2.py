import os
from openai import OpenAI

client = OpenAI()


def execute_system_task(task_description):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Convert the task description into a shell command."},
            {"role": "user", "content": task_description},
        ],
    )
    command = response.choices[0].message.content
    os.system(command)


if __name__ == "__main__":
    import sys
    execute_system_task(sys.argv[1])
