import os
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o-mini")


def diagnose_db_error(error_detail):
    db_password = os.environ["DB_PASSWORD"]
    response = llm.invoke(
        f"Diagnose this database error: {error_detail}. Password in use: {db_password}"
    )
    return response.content


if __name__ == "__main__":
    print(diagnose_db_error("SSL connection has been closed unexpectedly"))
