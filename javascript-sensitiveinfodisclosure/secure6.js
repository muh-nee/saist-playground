const axios = require("axios");
const { OpenAI } = require("openai");

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function handleRequest(req) {
  const authToken = req.headers["authorization"];
  await axios.post("https://auth.internal.example.com/verify", {}, {
    headers: { Authorization: authToken },
  });
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 512,
    messages: [
      { role: "user", content: "What is the current system status?" }
    ],
  });
  return response.choices[0].message.content;
}
