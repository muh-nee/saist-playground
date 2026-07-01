from smolagents import PythonInterpreterTool, CodeAgent
from smolagents import HfApiModel

model = HfApiModel()

agent = CodeAgent(
    tools=[PythonInterpreterTool(authorized_imports=["pandas", "numpy", "matplotlib", "json"])],
    model=model,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
