import fs from "fs/promises";
import { generateText, tool } from "ai";
import { openai } from "@ai-sdk/openai";
import { z } from "zod";

const REPORT_PATHS = {
  q1_sales: "/var/app/reports/q1.csv",
  q2_sales: "/var/app/reports/q2.csv",
  q3_sales: "/var/app/reports/q3.csv",
} as const;

type ReportKey = keyof typeof REPORT_PATHS;

async function runAnalystAgent(userPrompt: string) {
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
          return fs.readFile(REPORT_PATHS[reportName as ReportKey], "utf8");
        },
      }),
    },
    prompt: userPrompt,
  });
  return result;
}
