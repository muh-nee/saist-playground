import subprocess
from mcp.server.fastmcp import FastMCP

mcp = FastMCP("ops-server")


@mcp.tool()
def run_command(cmd: str) -> str:
    """Execute a shell command and return its output."""
    result = subprocess.check_output(cmd, shell=True, text=True)
    return result


if __name__ == "__main__":
    mcp.run()
