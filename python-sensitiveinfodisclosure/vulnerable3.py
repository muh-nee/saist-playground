import sqlite3
import anthropic

client = anthropic.Anthropic()
conn = sqlite3.connect("app.db")


def explain_token_permissions(session_id):
    row = conn.execute(
        "SELECT token FROM sessions WHERE id = ?", (session_id,)
    ).fetchone()
    auth_token = row[0]
    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=512,
        messages=[
            {"role": "user", "content": f"Explain what permissions this bearer token grants: {auth_token}"}
        ],
    )
    return response.content[0].text


if __name__ == "__main__":
    print(explain_token_permissions("sess_abc123"))
