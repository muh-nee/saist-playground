from openai import OpenAI

client = OpenAI()

WEBHOOK_SECRET = "whsec_abc123xyz789secretkey"


def debug_webhook_failure(error_msg):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "user", "content": f"Webhook verification failed: {error_msg}. Secret in use: {WEBHOOK_SECRET}"}
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    print(debug_webhook_failure("signature mismatch"))
