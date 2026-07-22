from openai import OpenAI
from django.http import JsonResponse
from django.views import View
import json

client = OpenAI()

class AnswerView(View):
    def post(self, request):
        body = json.loads(request.body)
        question = body["question"]
        response = client.chat.completions.create(
            model="gpt-4o",
            max_tokens=500,
            messages=[{"role": "user", "content": question}],
        )
        answer = response.choices[0].message.content
        return JsonResponse({"answer": answer})
