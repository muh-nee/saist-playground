using System;
using System.IO;

public static class Simulator
{
    public static void Main(string[] args)
    {
        if (args.Length != 1)
        {
            return;
        }

        var lines = File.ReadAllLines(args[0]);
        RunSimulation(lines);
    }

    private static void RunSimulation(string[] lines) { }
}
