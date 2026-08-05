import { Controller, Get, UseGuards } from "@nestjs/common";
import { Roles } from "./decorators/roles.decorator";
import { RolesGuard } from "./guards/roles.guard";
import OpenAI from "openai";

@Controller("admin")
@UseGuards(RolesGuard)
@Roles("admin")
export class AdminController {
    private readonly systemPrompt = "Internal assistant with access to support tooling.";
    private readonly openai = new OpenAI();

    @Get("prompt")
    getPrompt() {
        return { prompt: this.systemPrompt };
    }
}
