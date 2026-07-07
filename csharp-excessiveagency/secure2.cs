using System.ComponentModel;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class SafeFilePlugin
{
    private static readonly string AllowedRoot =
        Path.GetFullPath("/var/app/data");

    [KernelFunction("read_file")]
    [Description("Read a file from the data directory")]
    public string ReadFile(string relativePath)
    {
        var fullPath = Path.GetFullPath(Path.Combine(AllowedRoot, relativePath));
        if (!fullPath.StartsWith(AllowedRoot + Path.DirectorySeparatorChar, StringComparison.Ordinal))
            throw new UnauthorizedAccessException("path traversal detected");

        return File.ReadAllText(fullPath);
    }
}
