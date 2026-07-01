from crewai import Agent, Task, Crew
from crewai_tools import FileReadTool
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model="gpt-4o", temperature=0)

q1_report_tool = FileReadTool(file_path="/var/app/reports/q1_sales.csv")

analyst = Agent(
    role="Data Analyst",
    goal="Analyze sales data and produce a summary",
    backstory="You analyze sales reports and surface key insights.",
    tools=[q1_report_tool],
    llm=llm,
)

task = Task(
    description="Read the Q1 sales report and summarize the top 3 findings.",
    expected_output="A bullet-point summary of the top 3 findings.",
    agent=analyst,
)

crew = Crew(agents=[analyst], tasks=[task])

if __name__ == "__main__":
    print(crew.kickoff())
