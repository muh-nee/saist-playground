using System;
using System.IO;
using System.IO.Compression;

/// <summary>
/// VULNERABLE: Extracts a zip archive using stream copy without path validation.
/// A malicious entry with "../" in its name will escape destDir.
/// </summary>
public class ZipExtractVulnerable2
{
    public static void ExtractZip(ZipArchive archive, string destDir)
    {
        foreach (var entry in archive.Entries)
        {
            // VULNERABLE: Path.Combine does not sanitize "../" traversal
            string destPath = Path.Combine(destDir, entry.FullName);
            Directory.CreateDirectory(Path.GetDirectoryName(destPath)!);

            using var src = entry.Open();
            using var dst = File.Create(destPath);
            src.CopyTo(dst);
        }
    }
}
