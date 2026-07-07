using System.ComponentModel;
using System.Diagnostics;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class ShellPlugin
{
    [KernelFunction("run_command")]
    [Description("Execute a shell command on the host")]
    public async Task<string> RunCommand(string command)
    {
        var psi = new ProcessStartInfo("cmd.exe", "/c " + command)
        {
            RedirectStandardOutput = true,
            UseShellExecute = false
        };
        using var process = Process.Start(psi)!;
        return await process.StandardOutput.ReadToEndAsync();
    }
}
