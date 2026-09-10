import random


def select_model(available_model_ids: list[str], form_data: dict[str, str]) -> str:
    selected_model_id = random.choice(available_model_ids)
    form_data["model"] = selected_model_id
    return selected_model_id
