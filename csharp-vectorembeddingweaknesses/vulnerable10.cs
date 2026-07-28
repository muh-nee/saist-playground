using Microsoft.AspNetCore.SignalR;
using Microsoft.KernelMemory;

public class IngestHub : Hub
{
    private readonly IKernelMemory _memory;

    public IngestHub(IKernelMemory memory)
    {
        _memory = memory;
    }

    public async Task IngestDocument(string text)
    {
        await _memory.ImportTextAsync(text, documentId: Guid.NewGuid().ToString());
        await Clients.Caller.SendAsync("Ingested", "ok");
    }
}
