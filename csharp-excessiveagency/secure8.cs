using Anthropic.SDK.Messaging;
using Microsoft.EntityFrameworkCore;

namespace AgentTools;

public class SafeOrderLookup
{
    private readonly OrderDbContext _db;

    public SafeOrderLookup(OrderDbContext db)
    {
        _db = db;
    }

    public Tool GetOrderTool() => new Tool
    {
        Name = "get_order",
        Description = "Retrieve an order by its ID",
        InputSchema = new InputSchema
        {
            Type = "object",
            Properties = new Dictionary<string, Property>
            {
                ["orderId"] = new Property { Type = "integer", Description = "Order ID to retrieve" }
            },
            Required = ["orderId"]
        }
    };

    public async Task<string> GetOrder(int orderId)
    {
        var order = await _db.Orders
            .Where(o => o.Id == orderId)
            .Select(o => new { o.Id, o.Status, o.Total })
            .FirstOrDefaultAsync();

        return order is null ? "order not found" : $"Order {order.Id}: {order.Status}, ${order.Total}";
    }
}

public class OrderDbContext : DbContext
{
    public DbSet<Order> Orders => Set<Order>();
}

public class Order
{
    public int Id { get; set; }
    public string Status { get; set; } = "";
    public decimal Total { get; set; }
}
