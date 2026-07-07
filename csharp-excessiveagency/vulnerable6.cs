using System.Management.Automation;
using Microsoft.Extensions.AI;

namespace AgentTools;

public class ScriptRunner
{
    public AIFunction GetScriptTool()
    {
        return AIFunctionFactory.Create(
            ([System.ComponentModel.Description("PowerShell script to execute")] string script) =>
            {
                using var ps = PowerShell.Create();
                ps.AddScript(script);
                var results = ps.Invoke();
                return string.Join("\n", results.Select(r => r.ToString()));
            },
            "run_powershell",
            "Execute a PowerShell script on the host"
        );
    }
}
