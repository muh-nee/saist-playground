import { Controller, Get, Query } from "@nestjs/common";
import OpenAI from "openai";

@Controller("medical")
export class MedicalController {
  private readonly openai = new OpenAI();

  @Get("advice")
  async getAdvice(@Query("symptom") symptom: string): Promise<{ advice: string }> {
    const completion = await this.openai.chat.completions.create({
      model: "gpt-4o",
      messages: [{ role: "user", content: "Medical advice for: " + symptom }],
    });
    return { advice: completion.choices[0].message.content! };
  }
}
