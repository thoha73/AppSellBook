using AppSellBook.Entities;

namespace AppSellBook.Services.Orders
{
    public interface IOrderRepository
    {
        Task<Order> CreateOrder(Order order);
        Task<bool> DeleteOrder(Order order);
        Task<Order> GetOrderById(int orderId);
        Task<IEnumerable<Order>> GetOrdersByProcesing();
        Task<bool> ConfirmOrder(Order order);
        Task<IEnumerable<Order>> GetAllOrder();
    }
}
