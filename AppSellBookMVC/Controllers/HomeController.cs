using AppSellBookMVC.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using Newtonsoft.Json;
using System.Diagnostics;
using System.Net.Http;
using System.Net.WebSockets;
using System.Text;

namespace AppSellBookMVC.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly HttpClient _httpClient;
        private static string url = "https://localhost:44317/graphql/";
        private readonly IHubContext<BookHub> _bookHubContext;
        public HomeController(ILogger<HomeController> logger, HttpClient httpClient, IHubContext<BookHub> bookHubContext)
        {
            _logger = logger;
            _httpClient = httpClient;
            _bookHubContext = bookHubContext;
        }
        [HttpGet]
        public IActionResult Login()
        {
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> Login(string username, string password)
        {
            var graphQLRequest = new
            {
                query = $@"
                mutation {{
                  login(loginRequest: {{username: ""{username}"", password: ""{password}""}}) {{
                    userId
                    username
                    lastName
                    roleUsers {{
                      rolesroleId
                    }}
                  }}
                }}"
            };
            var content = new StringContent(JsonConvert.SerializeObject(graphQLRequest), Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync(url, content);

            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                Console.WriteLine(jsonResponse);  
                HttpContext.Session.SetString("Username", username);
                return RedirectToAction("Index");
            }
            else
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                ViewBag.ErrorMessage = errorResponse;
                return View();
            }
       }   
    public async Task<IActionResult>Index()
        {
            var query = new
            {
                query = @"
                    query{
                        bookCount
                        books {
                            bookId
                            bookName
                            description
                            isbn
                            listedPrice
                            publisher
                            sellPrice
                            quantity
                            author{
                                authorId
                                authorName
                            }
                            rank
                            images {
                                imageName
                                imageData
                                icon
                            }
                            categories{
                                categoryId
                                categoryName
                            }
                        }
                    }
                "
            };
            var queryJson=JsonConvert.SerializeObject(query);
            Console.WriteLine(queryJson);
            var content= new StringContent(queryJson,Encoding.UTF8,"application/json");
            try
            {
                var response = await _httpClient.PostAsync(url, content);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    Console.WriteLine(jsonResponse);
                    var result = JsonConvert.DeserializeObject<GraphQLResponse>(jsonResponse);
                    Console.WriteLine($"BookCount: {result?.data.bookCount}");
                    ViewBag.Count = result?.data.bookCount ?? 0;
                    ViewBag.Books = result?.data.books ?? new List<Book>();
                    ViewBag.Users = await GetUser();
                    ViewBag.Orders = await Orders();
                    await StartBookSubscription();
                    return View();
                }
                else
                {
                    ViewBag.Error = "Failed to load data from GraphQL API.";
                    return View();
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = $"An error occurred: {ex.Message}";
                return View();
            }
            
           
        }
        [HttpPost]
        public async Task<List<Order>> Orders()
        {
            var query = new
            {
                query = @"
        query {
            allOrders {
                orderId
                deliveryDate
                user {
                    firstName
                }
                orderDetails {
                    bookId
                    quantity
                    sellPrice
                }
            }
        }"
            };

            var queryJson = JsonConvert.SerializeObject(query);
            var content = new StringContent(queryJson, Encoding.UTF8, "application/json");

            try
            {
                var response = await _httpClient.PostAsync(url, content);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    var result = JsonConvert.DeserializeObject<GraphQLResponse>(jsonResponse);

                        if (result?.data?.allOrders == null)
                    {
                        ViewBag.Error = "No orders found.";
                        return null;
                    }

                    // Tính toán tổng tiền trước và gán vào ViewBag

                    var orders = result.data.allOrders
       .GroupBy(order => order.orderId)
       .Select(group => new
       {
           orderId = group.Key,
           deliveryDate = group.First().deliveryDate, // Lấy ngày giao hàng từ đơn hàng đầu tiên trong nhóm
           CustomerName = group.First().user?.firstName, // Lấy tên khách hàng từ đơn hàng đầu tiên trong nhóm
           TotalAmount = group.SelectMany(order => order.orderDetails)
                              .Sum(od => od.quantity * od.sellPrice)
       })
       .ToList();
                    List<Order> list = result?.data.allOrders ?? new List<Order>();                  
                    return list;
                }
                else
                {
                    var errorMessage = await response.Content.ReadAsStringAsync();
                    ViewBag.Error = $"Failed to load orders. Server returned: {errorMessage}";
                    return null;
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = $"An error occurred: {ex.Message}";
                return null;
            }
        }

        public async Task<List<User>> GetUser()
        {
            var query = new
            {
                query = @"
                    query{
                      users{
                        userId
                        point
                        deliveryAddress
                        username
                        password
                        gender
                        phone
                        email
                        isBlock
                      }
                    }
                "
            };
            var queryJson = JsonConvert.SerializeObject(query);
            Console.WriteLine(queryJson);
            var content = new StringContent(queryJson, Encoding.UTF8, "application/json");
            try
            {
                var response = await _httpClient.PostAsync(url, content);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    Console.WriteLine(jsonResponse);
                    var result = JsonConvert.DeserializeObject<GraphQLResponse>(jsonResponse);
                    Console.WriteLine($"BookCount: {result?.data.bookCount}");
                    ViewBag.BookCount = result?.data.bookCount ?? 0;
                    List<User> list = result?.data.users ?? new List<User>();
                    ViewBag.Users = list;
                    return list;
                }
                else
                {
                    ViewBag.Error = "Failed to load data from GraphQL API.";
                    return null;
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = $"An error occurred: {ex.Message}";
                return null;
            }
        }

        [HttpPost]
        public async Task<IActionResult> UpdateBlock(int userId, bool isBlock)
        {
            string block = isBlock.ToString().ToLowerInvariant();
            var query = new
            {
                query = $@"
                    mutation{{
                      updateLock(userId: {userId}, isBlock: {block} )
                    }}"
            };
            var queryJson = JsonConvert.SerializeObject(query);
            Console.WriteLine(queryJson);
            var content = new StringContent(queryJson, Encoding.UTF8, "application/json");
            try
            {
                var response = await _httpClient.PostAsync(url, content);

                if (response.IsSuccessStatusCode)
                {

                    return RedirectToAction("Index");
                }
                else
                {
                    ViewBag.Error = "Failed to load data from GraphQL API.";
                    return null;
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = $"An error occurred: {ex.Message}";
                return null;
            }
        }
            private async Task StartBookSubscription()
        {
            var query = new
            {
                query = @"
                    subscription {
                        bookCreated {
                            message
                            bookName
                            isbn
                            sellPrice
                            description
                        }
                    }
                "
            };
            var queryJson = JsonConvert.SerializeObject(query);
            var content = new StringContent(queryJson, Encoding.UTF8, "application/json");

            using (var webSocket = new ClientWebSocket())
            {
                try
                {
                    await webSocket.ConnectAsync(new Uri("wss://localhost:44317/graphql"), CancellationToken.None);
                    await webSocket.SendAsync(
                        new ArraySegment<byte>(Encoding.UTF8.GetBytes(queryJson)),
                        WebSocketMessageType.Text,
                        true,
                        CancellationToken.None);

                    var buffer = new byte[1024];
                    var result = await webSocket.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    var jsonResponse = Encoding.UTF8.GetString(buffer, 0, result.Count);
                    var subscriptionData = JsonConvert.DeserializeObject<GraphQLResponse>(jsonResponse);
                    if (subscriptionData?.data?.bookCreated != null)
                    {
                        _bookHubContext.Clients.All.SendAsync("ReceiveMessage",
                            subscriptionData.data.bookCreated.message,
                            subscriptionData.data.bookCreated.bookName,
                            subscriptionData.data.bookCreated.isbn,
                            subscriptionData.data.bookCreated.sellPrice,
                            subscriptionData.data.bookCreated.description);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error: {ex.Message}");
                }
            }
        }
        public IActionResult Privacy()
        {
            return View();
        }
        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
