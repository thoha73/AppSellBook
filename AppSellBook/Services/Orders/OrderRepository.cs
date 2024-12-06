using AppSellBook.Entities;
using Microsoft.EntityFrameworkCore;

namespace AppSellBook.Services.Orders
{
    public class OrderRepository : IOrderRepository
    {
        private readonly IDbContextFactory<BookDBContext> _contextFactory;
        public OrderRepository(IDbContextFactory<BookDBContext> contextFactory)
        {
            _contextFactory = contextFactory;
        }

        public async Task<bool> ConfirmOrder(Order order)
        {
            using (BookDBContext context = _contextFactory.CreateDbContext())
            {
                context.Orders.Update(order);
                
                return await context.SaveChangesAsync()>0;
            }
        }

        public async Task<Order> CreateOrder(Order order)
        {
            using(BookDBContext context=_contextFactory.CreateDbContext())
            {
                context.Orders.Add(order);
                await context.SaveChangesAsync();
                return order;
            }
            
        }

        public async Task<IEnumerable<Order>> GetAllOrder()
        {
            using (BookDBContext context = _contextFactory.CreateDbContext())
            {
                return await context.Orders.Include(u => u.user).Include(r => r.orderDetails).Where(o => o.orderStatus.Equals("Success")).ToListAsync();
            }
        }
        public async Task<bool> DeleteOrder(Order order)
        {
            using (BookDBContext context = _contextFactory.CreateDbContext())
            {
                context.Orders.Remove(order);
                return await context.SaveChangesAsync()>0;
            }
        }

        public async Task<Entities.Order> GetOrderById(int orderId)
        {
            using (BookDBContext context = _contextFactory.CreateDbContext())
            {
                
                return await context.Orders.Include(u => u.user).Include(o => o.orderDetails)
                                            .ThenInclude(b => b.book).FirstOrDefaultAsync(r=>r.orderId==orderId);
            }
        }

        public async Task<IEnumerable<Order>> GetOrdersByProcesing()
        {
            using (BookDBContext context = _contextFactory.CreateDbContext())
            {

                var orders= await context.Orders.Include(u=>u.user).Include(o=>o.orderDetails)
                                            .ThenInclude(b=>b.book)
                                            .Where(r=>r.orderStatus.Equals("Processing")).ToListAsync();
                return orders;
            }
        }
    }
}
