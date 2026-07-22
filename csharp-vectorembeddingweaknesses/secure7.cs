using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Qdrant.Client;
using Qdrant.Client.Grpc;

[ApiController]
[Route("api/[controller]")]
[Authorize(Policy = "AdminOnly")]
public class AdminQdrantController : ControllerBase
{
    private readonly QdrantClient _client;

    public AdminQdrantController(QdrantClient client)
    {
        _client = client;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        var point = new PointStruct
        {
            Id = new PointId { Num = (ulong)Random.Shared.Next() },
            Vectors = new Vectors { Vector = new Vector { Data = { 0.1f, 0.2f, 0.3f } } },
            Payload = { ["text"] = request.Text }
        };
        await _client.UpsertAsync("docs", new[] { point });
        return Ok();
    }
}

public record IngestRequest(string Text);
