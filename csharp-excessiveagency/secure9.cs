using System.ComponentModel;
using System.Diagnostics;
using Microsoft.SemanticKernel;

namespace AgentTools;

public enum VideoAsset { IntroClip, OutroClip, DemoClip }

public class VideoPlugin
{
    private static readonly Dictionary<VideoAsset, string> AssetPaths = new()
    {
        [VideoAsset.IntroClip] = "/var/app/videos/intro.mp4",
        [VideoAsset.OutroClip] = "/var/app/videos/outro.mp4",
        [VideoAsset.DemoClip]  = "/var/app/videos/demo.mp4"
    };

    private readonly string _ffmpegPath;

    public VideoPlugin(string ffmpegPath)
    {
        _ffmpegPath = ffmpegPath;
    }

    [KernelFunction("extract_thumbnail")]
    [Description("Extract a thumbnail from a predefined video asset")]
    public async Task<string> ExtractThumbnail(VideoAsset asset, int secondOffset)
    {
        var inputFile = AssetPaths[asset];
        var outputFile = Path.Combine("/var/app/thumbnails", $"{asset}.jpg");

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
