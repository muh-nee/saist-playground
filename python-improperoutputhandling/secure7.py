from openai import OpenAI

client = OpenAI()


def generate_report(data_summary):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": f"Write a plain text report for: {data_summary}"}],
    )
    report = response.choices[0].message.content
    with open("report.txt", "w") as f:
        f.write(report)
    return report


if __name__ == "__main__":
    import sys
    print(generate_report(sys.argv[1]))
