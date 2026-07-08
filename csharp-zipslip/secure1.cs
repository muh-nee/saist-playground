using System;
using System.IO;
using System.IO.Compression;

/// <summary>
/// Safe: Extracts a zip archive with Path.GetFullPath() validation.
/// Verifies each entry resolves within destDir before extraction.
/// </summary>
public class ZipExtractSafe1
{
    public static void ExtractZipSafe(string zipPath, string destDir)
    {
        // GetFullPath resolves ".." and returns an absolute path
        string fullDest = Path.GetFullPath(destDir) + Path.DirectorySeparatorChar;

        using var archive = ZipFile.OpenRead(zipPath);
        foreach (var entry in archive.Entries)
        {
            string destPath = Path.GetFullPath(Path.Combine(destDir, entry.FullName));

            // Safe: verify resolved path starts with destination directory
            if (!destPath.StartsWith(fullDest, StringComparison.OrdinalIgnoreCase))
            {
                throw new InvalidOperationException($"Illegal entry: {entry.FullName}");
            }

            if (entry.FullName.EndsWith("/"))
            {
                Directory.CreateDirectory(destPath);
            }
            else
            {
                Directory.CreateDirectory(Path.GetDirectoryName(destPath)!);
                entry.ExtractToFile(destPath, overwrite: true);
            }
        }
    }
}
