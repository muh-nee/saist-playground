const fs = require("fs/promises");
const { generateText, tool } = require("ai");
const { openai } = require("@ai-sdk/openai");
const { z } = require("zod");

const REPORT_PATHS = {
  q1_sales: "/var/app/reports/q1.csv",
  q2_sales: "/var/app/reports/q2.csv",
  q3_sales: "/var/app/reports/q3.csv",
};

async function runAnalystAgent(userPrompt) {
  const result = await generateText({
    model: openai("gpt-4o"),
    maxTokens: 2048,
    system: "You are a data analyst. Read sales data and produce a summary.",
    tools: {
      readReport: tool({
        description: "Read a quarterly sales report",
        parameters: z.object({
          reportName: z.enum(["q1_sales", "q2_sales", "q3_sales"]),
        }),
        execute: async ({ reportName }) => {
          return fs.readFile(REPORT_PATHS[reportName], "utf8");
        },
      }),
    },
    prompt: userPrompt,
  });
  return result;
}
