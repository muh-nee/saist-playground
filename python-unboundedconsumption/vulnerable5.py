from openai import AsyncOpenAI

client = AsyncOpenAI()

async def answer_async(user_message: str) -> str:
    response = await client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": user_message}],
    )
    return response.choices[0].message.content
