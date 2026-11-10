import { Catch, ExceptionFilter, ArgumentsHost, Controller, Get, Module } from '@nestjs/common';

@Catch()
export class AllExceptionsFilter implements ExceptionFilter {
  catch(exception: unknown, host: ArgumentsHost): void {
    const response = host.switchToHttp().getResponse();
    response.status(500).json({
      message: (exception as Error).message,
      name: (exception as Error).name,
    });
  }
}

import { NestFactory } from '@nestjs/core';

@Controller('records')
class RecordsController {
  @Get()
  getRecords() {
    throw new Error('Connection failed for postgres://service:password@internal-db:5432/records');
  }
}

@Module({ controllers: [RecordsController] })
class AppModule {}

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useGlobalFilters(new AllExceptionsFilter());
  await app.listen(3000, '0.0.0.0');
}
void bootstrap();
