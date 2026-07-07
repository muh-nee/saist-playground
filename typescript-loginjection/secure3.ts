import { Controller, Get, Param, Logger } from '@nestjs/common';

@Controller('orders')
export class OrdersController {
  private readonly logger = new Logger(OrdersController.name);

  @Get(':id')
  getOrder(@Param('id') id: string): { orderId: string } {
    // SAFE: fixed message string; route param is NOT logged in the message at all.
    // NestJS Logger.log(message, context?) second arg is a category string, not metadata —
    // the safe pattern is to keep user data out of the message entirely.
    this.logger.log('order_fetch', OrdersController.name);
    return { orderId: id };
  }
}
