import smtplib
from email.message import EmailMessage
from langchain.tools import tool
from langchain.agents import initialize_agent, AgentType
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

smtp = smtplib.SMTP("smtp.example.com", 587)
smtp.starttls()
smtp.login("agent@example.com", "...")


@tool
def send_email(to: str, subject: str, body: str) -> str:
    """Send an email notification to a user."""
    msg = EmailMessage()
    msg["From"] = "agent@example.com"
    msg["To"] = to
    msg["Subject"] = subject
    msg.set_content(body)
    smtp.send_message(msg)
    return f"Email sent to {to}"


agent = initialize_agent(
    tools=[send_email],
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
