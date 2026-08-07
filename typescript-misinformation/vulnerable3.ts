import { Controller, Post, Body } from "@nestjs/common";
import OpenAI from "openai";

class AskDto {
  question!: string;
}

@Controller("ask")
export class AskController {
  private readonly openai = new OpenAI();

  @Post()
  async ask(@Body() dto: AskDto): Promise<{ answer: string }> {
    const completion = await this.openai.chat.completions.create({
      model: "gpt-4o",
      messages: [{ role: "user", content: dto.question }],
    });
    return { answer: completion.choices[0].message.content! };
  }
}
