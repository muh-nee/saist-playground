import { Controller, Post, Body, UseGuards, ForbiddenException } from "@nestjs/common";
import { RolesGuard } from "./roles.guard";
import { Roles } from "./roles.decorator";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

class IngestDto {
  content!: string;
}

@Controller("knowledge")
@UseGuards(RolesGuard)
export class KnowledgeController {
  private readonly vectorStore = new Chroma(new OpenAIEmbeddings(), { collectionName: "docs" });

  @Post("ingest")
  @Roles("admin")
  async ingest(@Body() dto: IngestDto): Promise<{ status: string }> {
    await this.vectorStore.addDocuments([{ pageContent: dto.content, metadata: {} }]);
    return { status: "ok" };
  }
}
