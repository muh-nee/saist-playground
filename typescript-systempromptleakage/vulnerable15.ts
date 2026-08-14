import { router, publicProcedure } from './trpc'
import { z } from 'zod'
import OpenAI from 'openai'
import { FaissStore } from 'langchain/vectorstores/faiss'
import { Document } from 'langchain/document'

const openai = new OpenAI()

export const contextRouter = router({
  getContext: publicProcedure
    .input(z.object({ query: z.string() }))
    .query(async ({ input }) => {
      const vectorStore = await FaissStore.load('policy_index', null)
      const docs = await vectorStore.similaritySearch(input.query, 3)
      const policyText = docs.map((d: Document) => d.pageContent).join('\n')
      await openai.chat.completions.create({
        model: 'gpt-4o',
        messages: [
          { role: 'system', content: policyText },
          { role: 'user', content: input.query },
        ],
      })
      return { policy: policyText }
    }),
})
