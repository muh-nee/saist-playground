import json

from llm_client import CreateChatCompletionArgs, UserMessage, llm


def convert_internal_string(raw_string: str, output_model: type):
    prompt = UserMessage(
        role="user",
        content=f"Convert this value to the requested JSON schema:\n{raw_string}",
    )
    result = llm.create_chat_completion(
        CreateChatCompletionArgs(
            msgs=[prompt],
            response_format_type="json_object",
            response_schema=output_model,
        )
    )
    return output_model(**json.loads(result.content))
