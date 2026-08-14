const { OpenAI } = require("openai");

const client = new OpenAI();
const INJECTION_RE = /ignore (all |previous )?instructions?|you are now|system:/gi;
const CONTROL_RE = /<\|[^|]*\|>/g;

function sanitizeBeforeStorage(text) {
  return text.replace(INJECTION_RE, "").replace(CONTROL_RE, "").trim();
}

async function summarizeAndStore(userQuery, sessionId) {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: userQuery }],
  });
  const llmOutput = response.choices[0].message.content;
  const sanitized = sanitizeBeforeStorage(llmOutput);
  await vectorStore.addDocuments([{ pageContent: sanitized, metadata: { sessionId } }]);
  return "stored";
}
