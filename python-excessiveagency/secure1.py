import json
from openai import OpenAI

client = OpenAI()

REPORT_FILES = {
    "q1_sales": "/var/app/reports/q1_sales.csv",
    "q2_sales": "/var/app/reports/q2_sales.csv",
    "q3_sales": "/var/app/reports/q3_sales.csv",
    "q4_sales": "/var/app/reports/q4_sales.csv",
}


def read_report(report_name: str) -> str:
    path = REPORT_FILES[report_name]
    with open(path) as f:
        return f.read()


def handle_request(user_query: str) -> str:
    messages = [{"role": "user", "content": user_query}]
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=messages,
        tools=[{
            "type": "function",
            "function": {
                "name": "read_report",
                "description": "Read a quarterly sales report",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "report_name": {
                            "type": "string",
                            "enum": ["q1_sales", "q2_sales", "q3_sales", "q4_sales"],
                        }
                    },
                    "required": ["report_name"],
                },
            },
        }],
    )
    tool_call = response.choices[0].message.tool_calls[0]
    args = json.loads(tool_call.function.arguments)
    return read_report(args["report_name"])


if __name__ == "__main__":
    import sys
    print(handle_request(sys.argv[1]))
