namespace AppSellBookMVC.Models
{
    public class Order
    {
        public int orderId { get; set; }
        public DateTime deliveryDate { get; set; }
        public User user { get; set; }
        public List<OrderDetail> orderDetails { get; set; }
        public decimal TotalAmount => orderDetails?.Sum(od => od.quantity * od.sellPrice) ?? 0;
    }
    public class OrderDetail
    {
        public int bookId { get; set; }
        public int quantity { get; set; }
        public decimal sellPrice { get; set; }
    }
}