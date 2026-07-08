using System;
using System.IO;
using System.IO.Compression;

/// <summary>
/// VULNERABLE: Extracts a zip archive without validating entry paths.
/// entry.FullName may contain "../" sequences that escape destDir.
/// </summary>
public class ZipExtractVulnerable1
{
    public static void ExtractZip(string zipPath, string destDir)
    {
        using var archive = ZipFile.OpenRead(zipPath);
        foreach (var entry in archive.Entries)
        {
            // VULNERABLE: Path.Combine does not sanitize "../" in entry.FullName
            string destPath = Path.Combine(destDir, entry.FullName);

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
