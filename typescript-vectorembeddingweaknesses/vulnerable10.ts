import { Controller, Post, Body, UseGuards } from "@nestjs/common";
import { AuthGuard } from "@nestjs/passport";
import { ChromaClient, OpenAIEmbeddingFunction } from "chromadb";

class AddDocumentDto {
  id!: string;
  content!: string;
}

@Controller("docs")
export class DocsController {
  private readonly chroma = new ChromaClient({ path: "http://localhost:8000" });
  private readonly embedder = new OpenAIEmbeddingFunction({ openai_api_key: process.env.OPENAI_API_KEY! });

  @Post("add")
  @UseGuards(AuthGuard("jwt"))
  async addDocument(@Body() dto: AddDocumentDto): Promise<{ status: string }> {
    const collection = await this.chroma.getOrCreateCollection({ name: "shared_kb", embeddingFunction: this.embedder });
    await collection.add({
      ids: [dto.id],
      documents: [dto.content],
    });
    return { status: "added" };
  }
}
