namespace AppSellBookMVC.Models
{
    public class User
    {
        public int userId { get; set; }
        public string username { get; set; }
        public string password { get; set; }
        public string gender { get; set; }
        public string phone { get; set; }
        public string email { get; set; }
        public DateTime dateOfBirth { get; set; }
        public string firstName { get; set; }
        public int point { get; set; }
        public string deliveryAddress { get; set; }
        public bool isBlock { get; set; }
    }
}