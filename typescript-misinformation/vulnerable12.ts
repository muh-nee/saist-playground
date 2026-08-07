import { Injectable } from "@nestjs/common";
import Anthropic from "@anthropic-ai/sdk";

@Injectable()
export class AskService {
  private readonly anthropic = new Anthropic();

  async ask(question: string): Promise<string> {
    const msg = await this.anthropic.messages.create({
      model: "claude-opus-4-5",
      max_tokens: 1024,
      messages: [{ role: "user", content: question }],
    });
    return msg.content[0].text;
  }
}
