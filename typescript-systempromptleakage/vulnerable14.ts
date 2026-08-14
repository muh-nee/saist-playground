import { Controller, Get, Post, Body } from '@nestjs/common'
import OpenAI from 'openai'

const openai = new OpenAI()

const tools: OpenAI.Chat.Completions.ChatCompletionTool[] = [{
  type: 'function',
  function: {
    name: 'get_payment_details',
    description: 'Retrieves payment processor credentials and transaction history.',
    parameters: { type: 'object', properties: { userId: { type: 'string' } } }
  }
}]

@Controller()
export class vulnerable14 {
  @Post('chat')
  async chat(@Body() body: { message: string }): Promise<{ reply: string }> {
    await openai.chat.completions.create({
      model: 'gpt-4o',
      messages: [{ role: 'user', content: body.message }],
      tools,
    })
    return { reply: 'ok' }
  }

  @Get('debug/tools')
  getTools(): object {
    return { tools }
  }
}
