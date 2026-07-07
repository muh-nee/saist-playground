import { Controller, Get, Param, Logger } from '@nestjs/common';

@Controller('orders')
export class OrdersController {
  private readonly logger = new Logger(OrdersController.name);

  @Get(':id')
  getOrder(@Param('id') id: string): { orderId: string } {
    // VULNERABLE: route parameter interpolated directly into log message template literal
    this.logger.log(`Fetching order: ${id}`);
    return { orderId: id };
  }
}
