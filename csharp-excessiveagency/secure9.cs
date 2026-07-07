using System.ComponentModel;
using System.Diagnostics;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class VideoPlugin
{
    private readonly string _ffmpegPath;

    public VideoPlugin(string ffmpegPath)
    {
        _ffmpegPath = ffmpegPath;
    }

    [KernelFunction("extract_thumbnail")]
    [Description("Extract a thumbnail from a video at the given time offset")]
    public async Task<string> ExtractThumbnail(string inputFile, string outputFile, int secondOffset)
    {
        var psi = new ProcessStartInfo(_ffmpegPath)
        {
            UseShellExecute = false,
            RedirectStandardError = true
        };
        psi.ArgumentList.Add("-i");
        psi.ArgumentList.Add(inputFile);
        psi.ArgumentList.Add("-ss");
        psi.ArgumentList.Add(secondOffset.ToString());
        psi.ArgumentList.Add("-vframes");
        psi.ArgumentList.Add("1");
        psi.ArgumentList.Add(outputFile);

        using var process = Process.Start(psi)!;
        await process.WaitForExitAsync();
        return outputFile;
    }
}
