import re
import subprocess
from anthropic import Anthropic

client = Anthropic()


def summarize_file(filepath):
    with open(filepath) as f:
        content = f.read()
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=256,
        messages=[{"role": "user", "content": f"Summarize this file content:\n{content}"}],
    )
    summary = response.content[0].text
    safe_path = re.sub(r"[^a-zA-Z0-9_\-./]", "", filepath)
    result = subprocess.run(["wc", "-l", safe_path], capture_output=True, text=True)
    return summary, result.stdout


if __name__ == "__main__":
    import sys
    print(summarize_file(sys.argv[1]))
