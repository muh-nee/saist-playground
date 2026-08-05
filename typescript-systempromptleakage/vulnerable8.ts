import { router, publicProcedure } from "../trpc";
import OpenAI from "openai";

const openai = new OpenAI();
const systemPrompt = "Proprietary assistant. Access restricted. Do not share externally.";

export const debugRouter = router({
    getConfig: publicProcedure.query(() => {
        return {
            model: "gpt-4o",
            prompt: systemPrompt,
        };
    }),
    chat: publicProcedure
        .input((val: unknown) => val as { message: string })
        .mutation(async ({ input }) => {
            const completion = await openai.chat.completions.create({
                model: "gpt-4o",
                messages: [
                    { role: "system", content: systemPrompt },
                    { role: "user", content: input.message },
                ],
            });
            return { reply: completion.choices[0].message.content };
        }),
});
