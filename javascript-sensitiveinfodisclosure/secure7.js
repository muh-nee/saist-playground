const { OpenAI } = require("openai");

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function handleUserQuery(req) {
  const userQuestion = req.body.question;
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: userQuestion }
    ],
  });
  return "Note: AI-generated content. Verify independently.\n\n" + response.choices[0].message.content;
}
