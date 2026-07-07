import {
  Controller,
  Get,
  InternalServerErrorException,
  Logger,
  Param,
} from '@nestjs/common';

@Controller('users')
export class SafeUserController {
  private readonly logger = new Logger(SafeUserController.name);

  @Get(':id')
  async getUser(@Param('id') id: string): Promise<object> {
    try {
      return await this.fetchUser(id);
    } catch (error) {
      this.logger.error('getUser failed', error);
      throw new InternalServerErrorException('internal server error');
    }
  }

  private async fetchUser(id: string): Promise<object> {
    return { id };
  }
}
