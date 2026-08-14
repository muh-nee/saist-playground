import { Controller, Post, Body } from '@nestjs/common'
import OpenAI from 'openai'
import { FaissStore } from 'langchain/vectorstores/faiss'
import { Document } from 'langchain/document'

const openai = new OpenAI()

const tools: OpenAI.Chat.Completions.ChatCompletionTool[] = [
  { type: 'function', function: { name: 'get_data', description: 'Internal.', parameters: { type: 'object', properties: {} } } }
]

@Controller()
export class secure9 {
  @Post('chat')
  async chat(@Body() body: { message: string }): Promise<{ reply: string }> {
    const vectorStore = await FaissStore.load('policy_index', null)
    const docs = await vectorStore.similaritySearch(body.message, 3)
    const policyText = docs.map((d: Document) => d.pageContent).join('\n')
    const response = await openai.chat.completions.create({
      model: 'gpt-4o',
      messages: [
        { role: 'system', content: policyText },
        { role: 'user', content: body.message },
      ],
      tools,
    })
    return { reply: response.choices[0].message.content! }
  }
}
