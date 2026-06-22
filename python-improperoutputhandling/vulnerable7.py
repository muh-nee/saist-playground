import pickle
import base64
from openai import OpenAI

client = OpenAI()


def load_cached_result(task_description):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Output a base64-encoded pickle of a Python dict representing the cached result for the described task."},
            {"role": "user", "content": task_description},
        ],
    )
    encoded = response.choices[0].message.content.strip()
    return pickle.loads(base64.b64decode(encoded))  # sink: LLM-controlled pickle deserialization


if __name__ == "__main__":
    import sys
    print(load_cached_result(sys.argv[1]))
