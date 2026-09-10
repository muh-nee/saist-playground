from dataclasses import dataclass


@dataclass
class ContainerSpec:
    command: list[str]
    args: list[str]
    environment: dict[str, str]


def cleanup_container(namespace: str) -> ContainerSpec:
    return ContainerSpec(
        command=["/bin/bash", "-eu", "-o", "pipefail", "-c"],
        args=["kubectl delete jobs --field-selector=status.successful=1"],
        environment={"NAMESPACE": namespace},
    )
