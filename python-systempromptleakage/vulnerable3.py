from django.http import HttpResponse, JsonResponse
from openai import OpenAI

client = OpenAI()
system_prompt = "You are a support agent with access to internal ticket data and escalation paths."


def chat(request):
    user_message = request.POST.get("message", "")
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_message},
        ],
    )
    return JsonResponse({"reply": response.choices[0].message.content})


def get_prompt(request):
    return HttpResponse(system_prompt)
