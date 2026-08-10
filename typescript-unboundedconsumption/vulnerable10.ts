import { Injectable } from "@nestjs/common";
import OpenAI from "openai";

@Injectable()
export class AiService {
    private openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

    async generateReport(userInput: string): Promise<string> {
        const completion = await this.openai.chat.completions.create({
            model: "gpt-4o",
            messages: [
                { role: "system", content: "You generate detailed reports." },
                { role: "user", content: userInput },
            ],
        });
        return completion.choices[0].message.content ?? "";
    }
}
