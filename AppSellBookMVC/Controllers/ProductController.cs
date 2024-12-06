using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using Newtonsoft.Json;
using System.IO;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using AppSellBookMVC.Models;

namespace AppSellBookMVC.Controllers
{
    public class ProductController : Controller
    {
        private static string url = "https://localhost:44317/graphql/";
        private readonly HttpClient _httpClient;

        public ProductController(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }
        public IActionResult Index()
        {
            return View();
        }
        [HttpGet]
        public async Task<IActionResult> createProduct()
        {
            GetAuthor();
            var query = new
            {
                query = @"
                    query {
                        categories {
                            categoryId
                            categoryName
                        }
                    }"
            };
            var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");
            var response = await _httpClient.PostAsync(url, content);
            if (response.IsSuccessStatusCode)
            {
                var responseString = await response.Content.ReadAsStringAsync();
                var jsonResponse = JsonConvert.DeserializeObject<GraphQLResponse>(responseString);
                List<Category> list = jsonResponse?.data?.categories ?? new List<Category>();
                ViewBag.Categories = list;
                return View(list);
            }
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> createProduct(string tensach, string isbn, string nhaxuatban, double giaban, string mota,int categories,int author, IFormFile avatar, IFormFile anh1, IFormFile anh2, IFormFile anh3)
        {
            if (avatar != null && avatar.Length > 0)
            {

                async Task<byte[]> GetFileBytes(IFormFile file)
                {
                    using (var memoryStream = new MemoryStream())
                    {
                        await file.CopyToAsync(memoryStream);
                        return memoryStream.ToArray();
                    }
                }
                byte[] data = await GetFileBytes(avatar);
                byte[] data1 = anh1 != null ? await GetFileBytes(anh1) : null;
                byte[] data2 = anh2 != null ? await GetFileBytes(anh2) : null;
                byte[] data3 = anh3 != null ? await GetFileBytes(anh3) : null;
                Console.WriteLine(data);
                Console.WriteLine(data1);
                Console.WriteLine(data2);
                Console.WriteLine(data3);
                var query = new
                {
                    query = $@"
                    mutation {{
                        createBook(bookTypeInput: {{
                            bookId:0,
                            bookName: ""{tensach}"",
                            isbn: ""{isbn}"",
                            listedPrice:0 ,
                            sellPrice: {giaban},
                            publisher:""{nhaxuatban}"",
                            quantity: 100,
                            description: ""{mota}"",
                            rank: 5,
                            images: [
                                {{
                                    imageId:0                           
                                    imageName: ""avatar"",
                                    imageData: ""{Convert.ToBase64String(data)}"",
                                    icon: true,
                                    bookId: 1
                                }},
                                {{
                                    imageId:0 
                                    imageName: ""image1"",
                                    imageData: ""{Convert.ToBase64String(data1)}"",
                                    icon: false,
                                    bookId: 1
                                }},
                                {{
                                    imageId:0 
                                    imageName: ""image2"",
                                    imageData: ""{Convert.ToBase64String(data2)}"",
                                    icon: false,
                                    bookId: 1
                                }},
                                {{
                                    imageId:0 
                                    imageName: ""image3"",
                                    imageData: ""{Convert.ToBase64String(data3)}"",
                                    icon: false,
                                    bookId: 1
                                }}
                            ]
                        }},authorId:{author},categoryIds:{categories}) {{
                            bookId
                        }}
                    }}"

                };
                Console.WriteLine(query);
                var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");

                try
                {
                    var response = await _httpClient.PostAsync(url, content);
                    if (response.IsSuccessStatusCode)
                    {
                        var responseString = await response.Content.ReadAsStringAsync();

                        return RedirectToAction("index" , "Home");
                        
                    }
                    else
                    {
                        ModelState.AddModelError("", "Có lỗi xảy ra khi tạo sách.");
                        return View();
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error: {ex.Message}");
                    return View();
                }
            }
            return View();
                       
  
        }

        public async Task<IActionResult> Detail(int id)
        {
            var query = new
            {
                query = $@"
                    query {{
                      bookById(id: {id}) {{
                        bookId
                        bookName
                        description
                        isbn
                        listedPrice
                        publisher
                        sellPrice
                        quantity
                        author{{
                            authorId
                            authorName
                        }}
                        rank
                        images {{
                            imageId
                            imageName
                            imageData
                            icon
                        }}
                        categories{{
                            categoryId
                            categoryName
                        }}
                      }}
                    }}"
            };
            Console.WriteLine(query);
            var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");
            try
            {
                var response = await _httpClient.PostAsync(url, content);
                if (response.IsSuccessStatusCode)
                {
                    var responseString = await response.Content.ReadAsStringAsync();
                    var jsonRespone = JsonConvert.DeserializeObject<GraphQLResponse>(responseString);
                    var bookData = jsonRespone?.data?.bookById;
                    if (bookData != null)
                    {
                        Console.WriteLine("OK");
                        ViewBag.categories = await GetCategories();
                        var selectedCategories = bookData.categories.Select(c => c.categoryId).ToList();
                        ViewBag.selectedCategories = selectedCategories;

                        ViewBag.authors = await GetAuthor();
                        var selectedAuthor= bookData.author.authorId;
                        ViewBag.selectedAuthor = selectedAuthor;
                        return View(bookData);
                    }
                    else
                    {
                        Console.WriteLine("Lỗi");
                        return View();
                    }


                }
                else
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    Console.WriteLine($"Error Status Code: {response.StatusCode}");
                    Console.WriteLine($"Error Response: {errorResponse}");
                    Console.WriteLine("LỖi ở đây");
                    ModelState.AddModelError("", "Có lỗi xảy ra khi tạo sách.");
                    return View();
                }
            }
            catch
            {
                Console.WriteLine($"Error: Gặp lỗi");
                return View();
            }
        }
        public async Task<List<Category>> GetCategories()
        {
            var query = new
            {
                query = @"
                    query {
                        categories {
                            categoryId
                            categoryName
                        }
                    }"
            };
            var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");
            var response = await _httpClient.PostAsync(url, content);
            if (response.IsSuccessStatusCode)
            {
                var responseString = await response.Content.ReadAsStringAsync();
                var jsonResponse = JsonConvert.DeserializeObject<GraphQLResponse>(responseString);
                List<Category> list = jsonResponse?.data?.categories ?? new List<Category>();
                ViewBag.Categories = list;
                return list;
            }
            return new List<Category>();

        }
        public async Task<List<Author>> GetAuthor()
        {
            var query = new
            {
                query = @"
                    query{
                      authors{
                        authorId
                        authorName
                      }
                    }"
                                };
            var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");
            var response = await _httpClient.PostAsync(url, content);
            if (response.IsSuccessStatusCode)
            {
                var responseString = await response.Content.ReadAsStringAsync();
                var jsonResponse = JsonConvert.DeserializeObject<GraphQLResponse>(responseString);
                List<Author> list1 = jsonResponse?.data?.authors ?? new List<Author>();
                ViewBag.Authors = list1;
                return list1;
            }
            return new List<Author>();

        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> updateProduct(int id, int idanhavatar, int idanh1,int idanh2, int idanh3,string tensach, int tacgia, string isbn, string nhaxuatban, double giaban, string mota,int theloai, IFormFile avatar, IFormFile anh1, IFormFile anh2, IFormFile anh3)
        {
            if (avatar != null && avatar.Length > 0)
            {

                async Task<byte[]> GetFileBytes(IFormFile file)
                {
                    using (var memoryStream = new MemoryStream())
                    {
                        await file.CopyToAsync(memoryStream);
                        return memoryStream.ToArray();
                    }
                }
                byte[] data = await GetFileBytes(avatar);
                byte[] data1 = anh1 != null ? await GetFileBytes(anh1) : null;
                byte[] data2 = anh2 != null ? await GetFileBytes(anh2) : null;
                byte[] data3 = anh3 != null ? await GetFileBytes(anh3) : null;
                Console.WriteLine(data);
                Console.WriteLine(data1);
                Console.WriteLine(data2);
                Console.WriteLine(data3);
                var query = new
                {
                    query = $@"
                    mutation {{
                        updateBook(id:{id},bookTypeInput: {{
                            bookId:{id},
                            bookName: ""{tensach}"",
                            isbn: ""{isbn}"",
                            listedPrice:0 ,
                            sellPrice: {giaban},
                            publisher:""{nhaxuatban}"",
                            quantity: 100,
                            description: ""{mota}"",
                            rank: 5,
                            images: [
                                {{
                                    imageId:{idanhavatar},
                                    imageName: ""avatar"",
                                    imageData: ""{Convert.ToBase64String(data)}"",
                                    icon: true,
                                    bookId: 1
                                }},
                                {{
                                    imageId:{idanh1},
                                    imageName: ""image1"",
                                    imageData: ""{Convert.ToBase64String(data1)}"",
                                    icon: false,
                                    bookId: 1
                                }},
                                {{
                                    imageId:{idanh2},
                                    imageName: ""image2"",
                                    imageData: ""{Convert.ToBase64String(data2)}"",
                                    icon: false,
                                    bookId: 1
                                }},
                                {{
                                    imageId:{idanh3},
                                    imageName: ""image3"",
                                    imageData: ""{Convert.ToBase64String(data3)}"",
                                    icon: false,
                                    bookId: 1
                                }}
                            ]
                        }},authorId:{tacgia},categoryIds:{theloai}) {{
                            bookId
                        }}
                    }}"

                };
                Console.WriteLine(query);
                var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");

                try
                {
                    var response = await _httpClient.PostAsync(url, content);
                    if (response.IsSuccessStatusCode)
                    {
                        var responseString = await response.Content.ReadAsStringAsync();

                        return RedirectToAction("index", "Home");

                    }
                    else
                    {
                        ModelState.AddModelError("", "Có lỗi xảy ra khi cập nhật sách.");
                        return View();
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error: {ex.Message}");
                    return View();
                }
            }
            return RedirectToAction("Detail","Product");


        }

        [HttpDelete]
        public async Task<IActionResult> deleteProducts([FromBody]List<int> bookIds)
        {
            var bookIdsString = string.Join(", ", bookIds);
            var query = new
            {
                query = $@"
                    mutation {{
                        deleteBooks(bookIds: [{bookIdsString}]) 
                    }}
                "
            };
            var content = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");
            var response = await _httpClient.PostAsync(url, content);
            if (response.IsSuccessStatusCode)
            {
                return Ok(new { success = true });
            }
            else
            {
                var errorContent = await response.Content.ReadAsStringAsync();
                return BadRequest(new { success = false, message = errorContent });
            }
        }


    }

  
}


