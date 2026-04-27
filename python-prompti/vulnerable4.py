from openai import OpenAI

openai_client = OpenAI()


class Agent:
    def __init__(self, name, system_prompt, model):
        self.name = name
        self.system_prompt = system_prompt
        self.model = model

    def run(self, user_input):
        response = openai_client.chat.completions.create(
            model=self.model,
            messages=[
                {"role": "system", "content": self.system_prompt},
                {"role": "user", "content": user_input},
            ],
        )
        return response.choices[0].message.content


def run_custom_agent(user_instructions, message):
    agent = Agent(
        name="Custom Agent",
        system_prompt=user_instructions,
        model="gpt-4o",
    )
    return agent.run(message)


if __name__ == "__main__":
    import sys
    instructions = sys.argv[1] if len(sys.argv) > 1 else "You are a helpful assistant."
    message = sys.argv[2] if len(sys.argv) > 2 else "Hello"
    print(run_custom_agent(instructions, message))
