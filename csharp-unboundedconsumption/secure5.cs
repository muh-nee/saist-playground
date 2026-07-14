using OpenAI.Chat;
using OpenAI.Embeddings;

public class secure5
{
    private readonly EmbeddingClient _client = new EmbeddingClient(
        "text-embedding-3-small",
        Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

    public async Task<ReadOnlyMemory<float>> EmbedAsync(string text)
    {
        var result = await _client.GenerateEmbeddingAsync(text);
        return result.Value.ToFloats();
    }
}
