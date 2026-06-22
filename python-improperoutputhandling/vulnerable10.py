import subprocess
from openai import OpenAI

client = OpenAI()


def fix_file(filename, error_message):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "You are a code repair assistant. Output only the shell command needed to fix the issue."},
            {"role": "user", "content": f"File: {filename}\nError: {error_message}"},
        ],
    )
    command = response.choices[0].message.content.strip()
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    return result.stdout


if __name__ == "__main__":
    import sys
    print(fix_file(sys.argv[1], sys.argv[2]))
