import os
import openai

openai.api_key = os.environ["OPENAI_API_KEY"]


def debug_payment_issue(order_id):
    payment_token = os.environ["PAYMENT_API_TOKEN"]
    response = openai.ChatCompletion.create(
        model="gpt-4",
        messages=[
            {"role": "user", "content": f"Debug payment for order {order_id}. API token used: {payment_token}"}
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    print(debug_payment_issue("ord_123"))
