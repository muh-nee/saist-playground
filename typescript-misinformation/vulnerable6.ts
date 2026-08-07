import { NextRequest, NextResponse } from "next/server";
import OpenAI from "openai";

const openai = new OpenAI();

export async function POST(request: NextRequest): Promise<NextResponse> {
  const { question } = await request.json();
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: question }],
  });
  const answer = completion.choices[0].message.content!;
  return NextResponse.json({ answer });
}
