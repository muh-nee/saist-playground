#!/usr/bin/env python3

import subprocess
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import uvicorn

app = FastAPI(title="Vulnerable System API")

class CommandRequest(BaseModel):
    command: str
    timeout: int = 30

class SystemCommand(BaseModel):
    operation: str
    target: str
    options: str = ""

@app.post("/system/execute")
async def execute_system_command(request: CommandRequest):
    try:
        result = subprocess.run(
            request.command, 
            shell=True, 
            capture_output=True, 
            text=True, 
            timeout=request.timeout
        )
        
        return {
            "command": request.command,
            "stdout": result.stdout,
            "stderr": result.stderr,
            "returncode": result.returncode
        }
    except subprocess.TimeoutExpired:
        raise HTTPException(status_code=408, detail="Command timeout")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/system/file-operation")
async def file_operation(request: SystemCommand):
    if request.operation == "read":
        command = f"cat {request.target} {request.options}"
    elif request.operation == "delete":
        command = f"rm {request.options} {request.target}"
    elif request.operation == "copy":
        command = f"cp {request.options} {request.target}"
    else:
        raise HTTPException(status_code=400, detail="Invalid operation")
    
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    
    return {
        "operation": request.operation,
        "command": command,
        "output": result.stdout,
        "error": result.stderr
    }

@app.get("/system/ping/{host}")
async def ping_host(host: str, count: int = 3):
    command = f"ping -c {count} {host}"
    
    try:
        result = subprocess.run(command, shell=True, capture_output=True, text=True, timeout=10)
        return {
            "host": host,
            "command": command,
            "output": result.stdout,
            "success": result.returncode == 0
        }
    except subprocess.TimeoutExpired:
        raise HTTPException(status_code=408, detail="Ping timeout")

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)