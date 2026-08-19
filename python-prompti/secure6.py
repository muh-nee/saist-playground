from llm_client import AWSBedrockClaudeLLM
from models import CustomerFraudReasoningAgentRunOutput


def convert_agent_output(agent_output: str) -> CustomerFraudReasoningAgentRunOutput:
    llm = AWSBedrockClaudeLLM()
    prompt = f"""
    Extract the Analysis and Risk fields verbatim into the output model.

    <input_string>
    {agent_output}
    </input_string>
    """
    return llm.create_chat_completion_with_response_model(
        response_model=CustomerFraudReasoningAgentRunOutput,
        prompt=prompt,
    )
