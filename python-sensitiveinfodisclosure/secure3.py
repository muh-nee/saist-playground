import os
import psycopg2
import anthropic

client = anthropic.Anthropic()


def get_db_help(error_msg):
    db_password = os.environ["DB_PASSWORD"]
    db_host = os.environ["DB_HOST"]
    conn_string = f"postgresql://app:{db_password}@{db_host}/prod"
    db_conn = psycopg2.connect(conn_string)
    db_conn.close()

    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=512,
        messages=[{"role": "user", "content": f"Help me fix this DB error: {error_msg}"}],
    )
    return response.content[0].text


if __name__ == "__main__":
    print(get_db_help("remaining connection slots are reserved"))
