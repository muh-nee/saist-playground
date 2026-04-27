from openai import OpenAI

client = OpenAI()

ANALYSIS_SYSTEM_PROMPT = "You are a data analysis assistant. Analyze the provided metrics."


def analyze(context_name, start_time, end_time, user_message):
    user_prompt = f"Context: {context_name}\nTime range: {start_time} to {end_time}"
    if user_message:
        user_prompt += f"\n\n{user_message}"

    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": ANALYSIS_SYSTEM_PROMPT},
            {"role": "user", "content": user_prompt},
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    import sys
    context_name = sys.argv[1] if len(sys.argv) > 1 else "checkout"
    start_time = sys.argv[2] if len(sys.argv) > 2 else "2024-01-01"
    end_time = sys.argv[3] if len(sys.argv) > 3 else "2024-01-31"
    user_message = sys.argv[4] if len(sys.argv) > 4 else ""
    print(analyze(context_name, start_time, end_time, user_message))
