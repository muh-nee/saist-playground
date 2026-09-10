from flask import request


async def forward_user_turn(worker_dispatcher, current_org):
    await worker_dispatcher.dispatch_agent(
        org_id=current_org.id,
        agent_role="assistant",
        trigger_source="chat",
        prompt=request.json["message"],
    )
