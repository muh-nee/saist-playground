import { Controller, Get, Post, Body } from "@nestjs/common";
import OpenAI from "openai";

@Controller("debug")
export class DebugController {
    private readonly systemPrompt = "Internal assistant. Has access to customer records and support escalation paths.";
    private readonly openai = new OpenAI();

    @Post("chat")
    async chat(@Body() body: { message: string }) {
        const completion = await this.openai.chat.completions.create({
            model: "gpt-4o",
            messages: [
                { role: "system", content: this.systemPrompt },
                { role: "user", content: body.message },
            ],
        });
        return { reply: completion.choices[0].message.content };
    }

    @Get("config")
    getConfig() {
        return {
            model: "gpt-4o",
            prompt: this.systemPrompt,
        };
    }
}
