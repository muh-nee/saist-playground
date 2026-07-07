using Anthropic.SDK.Messaging;

namespace AgentTools;

public class FileWriteAgent
{
    public Tool GetWriteTool() => new Tool
    {
        Name = "write_file",
        Description = "Write content to a file at the given path",
        InputSchema = new InputSchema
        {
            Type = "object",
            Properties = new Dictionary<string, Property>
            {
                ["path"] = new Property { Type = "string", Description = "File path to write" },
                ["content"] = new Property { Type = "string", Description = "Content to write" }
            },
            Required = ["path", "content"]
        }
    };

    public void WriteFile(string path, string content)
    {
        File.WriteAllText(path, content);
    }
}
