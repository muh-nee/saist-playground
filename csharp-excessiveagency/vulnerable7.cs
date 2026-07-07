using ModelContextProtocol.Server;
using System.ComponentModel;

namespace AgentTools;

[McpServerToolType]
public class EnvironmentInspector
{
    [McpServerTool(Name = "get_env_var")]
    [Description("Read an environment variable by name")]
    public string GetEnvVar(string name)
    {
        return Environment.GetEnvironmentVariable(name) ?? "";
    }
}
