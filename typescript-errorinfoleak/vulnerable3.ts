import { Controller, Get, HttpException, HttpStatus, Param } from '@nestjs/common';

@Controller('users')
export class UserController {
  @Get(':id')
  async getUser(@Param('id') id: string): Promise<object> {
    try {
      return await this.fetchUser(id);
    } catch (error) {
      throw new HttpException((error as Error).message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private async fetchUser(id: string): Promise<object> {
    return { id };
  }
}
