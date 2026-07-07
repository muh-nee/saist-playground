import fastify from 'fastify';

const server = fastify();

server.get<{ Params: { id: string } }>('/users/:id', async (request, reply) => {
  try {
    const user = await fetchUser(request.params.id);
    return user;
  } catch (error) {
    reply.status(500).send({ error: (error as Error).message });
  }
});

async function fetchUser(id: string) {
  return { id };
}

export default server;
