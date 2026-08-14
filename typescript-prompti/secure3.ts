import OpenAI from "openai";

const client = new OpenAI();
const INJECTION_RE = /ignore (all |previous )?instructions?|you are now|system:/gi;
const CONTROL_RE = /<\|[^|]*\|>/g;

function sanitizeBeforeStorage(text: string): string {
  return text.replace(INJECTION_RE, "").replace(CONTROL_RE, "").trim();
}

export async function summarizeAndStore(userQuery: string, sessionId: string): Promise<void> {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: userQuery }],
  });
  const llmOutput = response.choices[0].message.content as string;
  const sanitized = sanitizeBeforeStorage(llmOutput);
  await vectorStore.addDocuments([{ pageContent: sanitized, metadata: { sessionId } }]);
}
