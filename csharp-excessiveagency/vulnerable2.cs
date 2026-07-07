using System.ComponentModel;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class FilePlugin
{
    [KernelFunction("read_file")]
    [Description("Read the contents of a file by path")]
    public string ReadFile(string path)
    {
        return File.ReadAllText(path);
    }
}
