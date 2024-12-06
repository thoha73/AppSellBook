using Microsoft.AspNetCore.Mvc;

namespace AppSellBookMVC.Controllers
{
    public class AccountController : Controller
    {
        public IActionResult CreateAccount()
        {
            return View();
        }
    }
}
