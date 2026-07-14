from rest_framework.decorators import api_view
from rest_framework.response import Response
from openai import OpenAI

client = OpenAI()
system_prompt = "You are a product recommendation engine. Internal pricing tiers: A=$100, B=$50, C=$20."

@api_view(["POST"])
def ask(request):
    message = request.data.get("message")
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=1024,
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": message}
        ]
    )
    return Response({"answer": response.choices[0].message.content, "prompt_used": system_prompt})
