using System.ComponentModel;
using System.Diagnostics;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class SafeShellPlugin
{
    private static readonly Dictionary<string, string[]> AllowedCommands = new()
    {
        ["df"]       = [],
        ["free"]     = [],
        ["uptime"]   = [],
        ["hostname"] = []
    };

    [KernelFunction("run_diagnostic")]
    [Description("Run an approved diagnostic command")]
    public async Task<string> RunDiagnostic(string command)
    {
        if (!AllowedCommands.TryGetValue(command, out var args))
            throw new ArgumentException($"command '{command}' is not in the allowlist");

        var psi = new ProcessStartInfo(command)
        {
            RedirectStandardOutput = true,
            UseShellExecute = false
        };
        foreach (var arg in args)
            psi.ArgumentList.Add(arg);

        using var process = Process.Start(psi)!;
        return await process.StandardOutput.ReadToEndAsync();
    }
}

