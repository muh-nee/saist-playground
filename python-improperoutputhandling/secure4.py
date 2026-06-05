import subprocess
from openai import OpenAI

client = OpenAI()

ALLOWED_COMMANDS = {"list_files": ["ls", "-la"], "disk_usage": ["df", "-h"], "uptime": ["uptime"]}


def run_approved_command(task):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": f"Pick the best command key from: {list(ALLOWED_COMMANDS.keys())}. Output the key only."},
            {"role": "user", "content": task},
        ],
    )
    key = response.choices[0].message.content.strip()
    if key not in ALLOWED_COMMANDS:
        raise ValueError(f"Unsupported command: {key}")
    result = subprocess.run(ALLOWED_COMMANDS[key], capture_output=True, text=True)
    return result.stdout


if __name__ == "__main__":
    import sys
    print(run_approved_command(sys.argv[1]))
