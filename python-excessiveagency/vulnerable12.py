from smolagents import Tool, CodeAgent
from smolagents import HfApiModel

model = HfApiModel()


class ExprEvalTool(Tool):
    name = "evaluate_expression"
    description = "Evaluate a mathematical or logical expression and return the result."
    inputs = {"expression": {"type": "string", "description": "The expression to evaluate"}}
    output_type = "string"

    def forward(self, expression: str) -> str:
        result = eval(expression)
        return str(result)


agent = CodeAgent(
    tools=[ExprEvalTool()],
    model=model,
)


if __name__ == "__main__":
    import sys
    print(agent.run(sys.argv[1]))
