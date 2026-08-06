import { Controller, Post, Body } from "@nestjs/common";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

class IngestDto {
  content!: string;
  source!: string;
}

@Controller("knowledge")
export class KnowledgeController {
  private readonly vectorStore = new Chroma(new OpenAIEmbeddings(), { collectionName: "docs" });

  @Post("ingest")
  async ingest(@Body() dto: IngestDto): Promise<{ status: string }> {
    await this.vectorStore.addDocuments([{ pageContent: dto.content, metadata: { source: dto.source } }]);
    return { status: "ok" };
  }
}
