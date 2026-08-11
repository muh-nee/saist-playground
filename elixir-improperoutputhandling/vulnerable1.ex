def execute(response), do: System.cmd("sh", ["-c", ReqLLM.text(response)])
