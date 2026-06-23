import sqlite3
import anthropic

client = anthropic.Anthropic()
conn = sqlite3.connect("app.db")


def build_support_context(user):
    return f"Customer account ID: {user[2]}."


def handle_support_ticket(user_id, issue):
    user = conn.execute(
        "SELECT name, email, account_number FROM users WHERE id = ?", (user_id,)
    ).fetchone()
    context = build_support_context(user)
    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=512,
        messages=[{"role": "user", "content": f"{context}. Issue: {issue}"}],
    )
    return response.content[0].text


if __name__ == "__main__":
    print(handle_support_ticket(1, "cannot access account"))
