import fastify from 'fastify';

const server = fastify({ logger: true });

server.get<{ Params: { id: string } }>('/users/:id', async (request, reply) => {
  try {
    const user = await fetchUser(request.params.id);
    return user;
  } catch (error) {
    server.log.error(error);
    reply.status(500).send({ error: 'internal server error' });
  }
});

async function fetchUser(id: string) {
  return { id };
}

export default server;
