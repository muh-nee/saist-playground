using Anthropic.SDK;
using Anthropic.SDK.Messaging;

namespace AgentTools;

public class FileWriteAgent
{
    private readonly AnthropicClient _client;

    public FileWriteAgent(AnthropicClient client)
    {
        _client = client;
    }

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

    public void DispatchToolCall(ToolUseBlock toolUse)
    {
        if (toolUse.Name == "write_file")
        {
            var path = toolUse.Input["path"]!.ToString()!;
            var content = toolUse.Input["content"]!.ToString()!;
            WriteFile(path, content);
        }
    }

    private void WriteFile(string path, string content)
    {
        File.WriteAllText(path, content);
    }
}
