import { Injectable } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import OpenAI from "openai";

@Injectable()
export class AiService {
    private openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });
    private maxTokens: number;

    constructor(private config: ConfigService) {
        this.maxTokens = this.config.get<number>("llm.maxTokens") ?? 512;
    }

    async generateReport(userInput: string): Promise<string> {
        const completion = await this.openai.chat.completions.create({
            model: "gpt-4o",
            messages: [
                { role: "system", content: "You generate detailed reports." },
                { role: "user", content: userInput },
            ],
            max_tokens: this.maxTokens,
        });
        return completion.choices[0].message.content ?? "";
    }
}
