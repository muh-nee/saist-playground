using System;
using System.IO;
using System.IO.Compression;

/// <summary>
/// Safe: Uses ZipFile.ExtractToDirectory() which validates paths in .NET 5+.
/// This is the recommended approach for trusted and untrusted archives.
/// </summary>
public class ZipExtractSafe2
{
    public static void ExtractZipSafe(string zipPath, string destDir)
    {
        // Safe: ZipFile.ExtractToDirectory() validates paths in .NET 5+
        // and throws an exception if any entry would escape the destination
        ZipFile.ExtractToDirectory(zipPath, destDir, overwriteFiles: true);
    }
}
