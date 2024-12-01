package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Author;
import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.DTOs.Category;
import com.example.appsellbook.DTOs.Commentation;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.CurrencyFormat;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.CommentArrayAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail  extends BaseActivity {
    private TextView tv_nameBook,tv_price,tv_author,tv_isbn,tv_description,tv_publisher,tv_category;
    private ImageView img,img_heart;
    private boolean isRedHeart = false;
    private Button btn_review;
    private RecyclerView recyclerView;
    private CommentArrayAdapter commentArrayAdapter;
    public List<String > listImageReview;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        img = findViewById(R.id.imageView1);
        tv_nameBook =findViewById(R.id.textViewNameProduct);
        tv_price = findViewById(R.id.textViewPriceProduct);
        tv_author= findViewById(R.id.textViewContentAuthor);
        tv_description = findViewById(R.id.textViewContentDescription);
        tv_isbn = findViewById(R.id.textViewContentISBN);
        tv_category=findViewById(R.id.textViewContentCategory);
        tv_publisher=findViewById(R.id.textViewContentPublisher);
        listImageReview= new ArrayList<String>();

        btn_review = findViewById(R.id.btn_review);
        recyclerView = findViewById(R.id.listview_comment);
        ImageView img_cart;
        img_cart = findViewById(R.id.imageView3);
        img_cart.setOnClickListener(view -> {
            startActivity(new Intent(ProductDetail.this, Cart.class));
        });
        Intent intent = getIntent();
        ImageView img_heart = findViewById(R.id.imageView2);


        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_profile){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });

        int bookId = getIntent().getIntExtra("BookId", 0);
        if (bookId != 0) {
            fetchBookDetails(bookId);
            getCommentationInBook(bookId);
            SessionManager sessionManager = new SessionManager(ProductDetail.this);
            int userId= sessionManager.getUserId();
            img_heart.setOnClickListener(v -> {
                if (isRedHeart) {
                    img_heart.setImageResource(R.drawable.heart);
                } else {
                    img_heart.setImageResource(R.drawable.heart_red);
                    addWishList(userId,bookId);
                }
                isRedHeart = !isRedHeart;
            });
            img_cart.setOnClickListener(v->{
                addCart(userId,bookId);
            });
        }

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(listImageReview);
                editor.putString("listImageReview", json);
                editor.apply();

                Intent intent = new Intent(ProductDetail.this, Review.class);
                startActivity(intent);
            }
        });
    }
    private void fetchBookDetails(int bookId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " query{\n" +
                "  bookById(id:"+bookId+"){\n" +
                "    bookId\n" +
                "    bookName\n" +
                "    isbn\n" +
                "    listedPrice\n" +
                "    sellPrice\n" +
                "    quantity\n" +
                "    description\n" +
                "    publisher\n" +
                "    rank\n" +
                "    images{\n" +
                "      imageId\n" +
                "      imageName\n" +
                "      imageData\n" +
                "      icon\n" +
                "    }\n" +
                "    author{\n" +
                "      authorName\n" +
                "    }\n"+
                "    categories{\n" +
                "      categoryId\n" +
                "      categoryName\n" +
                "    }"+
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> bookData = (LinkedTreeMap<String, Object>) data.getData();

                        // Assuming 'bookById' is in the map, extract it manually
                        LinkedTreeMap<String, Object> bookById = (LinkedTreeMap<String, Object>) bookData.get("bookById");

                        if (bookById != null) {
                            Book book = new Book();
                            book.setBookId(((Double) bookById.get("bookId")).intValue());
                            book.setBookName((String) bookById.get("bookName"));
                            book.setIsbn((String) bookById.get("isbn"));
                            book.setListedPrice(((Double) bookById.get("listedPrice")).floatValue());
                            book.setSellPrice(((Double) bookById.get("sellPrice")).floatValue());
                            book.setQuantity(((Double) bookById.get("quantity")).intValue());
                            book.setDescription((String) bookById.get("description"));
                            book.setPublisher((String) bookById.get("publisher"));
                            book.setRank(((Double) bookById.get("rank")).intValue());
                            List<LinkedTreeMap<String, Object>> images = (List<LinkedTreeMap<String, Object>>) bookById.get("images");
                            if (images != null && !images.isEmpty()) {
                                String base64Image = (String) images.get(0).get("imageData");
                                String img1=(String) images.get(1).get("imageData");
                                String img2=(String) images.get(2).get("imageData");
                                String img3=(String) images.get(3).get("imageData");
                                listImageReview.add(img1);
                                listImageReview.add(img2);
                                listImageReview.add(img3);
                                setImageFromBase64(base64Image);
                            }
                            LinkedTreeMap<String ,Object> author = (LinkedTreeMap<String, Object>) bookById.get("author");
                            if (author != null) {
                                Author authors= new Author();
                                String authorName = (String) author.get("authorName");
                                authors.setAuthorName(authorName);
                                book.setAuthor(authors);
                            }
                            List<LinkedTreeMap<String, Object>> categories = (List<LinkedTreeMap<String, Object>>) bookById.get("categories");
                            if(categories!=null && !categories.isEmpty()){
                                List<Category> categoryList = new ArrayList<>();
                                for(LinkedTreeMap<String, Object> category : categories){
                                    Category category1 = new Category();
                                    category1.setCategoryId(((Double)category.get("categoryId")).intValue());
                                    category1.setCategoryName((String)category.get("categoryName"));
                                    categoryList.add(category1);
                                };
                                book.setCategories(categoryList);
                            }
                            tv_author.setText("Tác giả: "+author.get("authorName").toString());
                            tv_description.setText(book.getDescription());
                            tv_nameBook.setText(book.getBookName());
                            tv_price.setText(CurrencyFormat.formatCurrency(book.getSellPrice()));;;
                            tv_isbn.setText("ISBN: "+book.getIsbn());
                            tv_category.setText("Thể loại: "+book.getCategories().get(0).getCategoryName());
                            tv_publisher.setText("Nhà xuất bản: "+book.getPublisher());

                        }
                    } else {
                        Log.d("Error", "Unexpected data format: " + data.getData());
                    }
                } else {
                    Log.d("Error", "Failed to load book details: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error fetching book details", t);
            }
        });
    }
    private void setImageFromBase64(String base64Image) {
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedBitmap);
        }
    }
    private void getCommentationInBook(int bookId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query {\n" +
                "  commentationsByBookId(bookId: " + bookId + ") {\n" +
                "    content\n" +
                "    rank\n" +
                "    user {\n" +
                "      lastName\n" +
                "      firstName\n" +
                "    }\n" +
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();

                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Object commentations = dataMap.get("commentationsByBookId");

                        if (commentations instanceof ArrayList) {
                            List<Commentation> commentationList = new ArrayList<>();
                            ArrayList<?> commentationArray = (ArrayList<?>) commentations;

                            for (Object commentObj : commentationArray) {
                                if (commentObj instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> commentMap = (LinkedTreeMap<String, Object>) commentObj;

                                    // Tạo đối tượng Commentation
                                    Commentation commentation = new Commentation();
                                    commentation.setCommentationContent((String) commentMap.get("content"));
                                    commentation.setRank(((Double) commentMap.get("rank")).intValue());

                                    // Lấy thông tin người dùng
                                    Object userObj = commentMap.get("user");
                                    if (userObj instanceof LinkedTreeMap) {
                                        LinkedTreeMap<String, Object> userMap = (LinkedTreeMap<String, Object>) userObj;
                                        User user = new User();
                                        user.setFirstName((String) userMap.get("firstName"));
//                                        user.setLastName((String) userMap.get("lastName"));
                                        commentation.setUser(user);
                                    }

                                    commentationList.add(commentation);
                                }
                            }

                            // Hiển thị danh sách bình luận bằng Adapter

                            recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetail.this));
                            CommentArrayAdapter adapter = new CommentArrayAdapter(ProductDetail.this, new ArrayList<>(commentationList));
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("Error", "commentationsByBookId không phải là danh sách");
                        }
                    } else {
                        Log.e("Error", "Dữ liệu phản hồi không đúng định dạng: " + data.getData());
                    }
                } else {
                    Log.e("Error", "Phản hồi thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Lỗi khi tải dữ liệu", t);
            }
        });
    }
    private void addWishList(int userId, int bookId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "mutation {\n" +
                "  addWishlist(userId: " + userId + ", bookId: " + bookId + ") {\n" +
                "    wishListId\n" +
                "    wishListName\n" +
                "    userId\n" +  // Đảm bảo API trả về trường userId nếu cần
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                        // Nếu có lỗi trong response, hiển thị lỗi
                        String errorMessage = data.getErrors().get(0).getMessage();
                            new AlertDialog.Builder(ProductDetail.this)
                                    .setTitle("Thông báo")
                                    .setMessage(errorMessage)
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .show();
                    } else {
                        try {
                            if (data.getData() instanceof LinkedTreeMap) {
                                LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                                Object wishList = dataMap.get("addWishlist");

                                if (wishList instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> wishListMap = (LinkedTreeMap<String, Object>) wishList;

                                    // Lấy và kiểm tra các trường từ map
                                    Double wishListIdValue = (Double) wishListMap.get("wishListId");
                                    String wishListName = (String) wishListMap.get("wishListName");
                                    Double userIdValue = (Double) wishListMap.get("userId");

                                    if (wishListIdValue != null && userIdValue != null && wishListName != null) {
                                        int wishListId = wishListIdValue.intValue(); // Chuyển đổi Double thành int
                                        int userId = userIdValue.intValue();

                                        // Hiển thị thông báo thành công
                                        new AlertDialog.Builder(ProductDetail.this)
                                        .setTitle("Thông báo")
                                        .setMessage("Sách đã được thêm vào danh sách yêu thích!\n")
                                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                        .show();
                                    } else {
                                        Log.e("Error","Dữ liệu trả về chứa giá trị null cho wishListId, userId hoặc wishListName.");
                                    }
                                } else {
                                    Log.e("Error","Dữ liệu 'addWishlist' không phải LinkedTreeMap: " + wishList);
                                }
                            } else {
                                Log.e("Error","Dữ liệu phản hồi không đúng định dạng: " + data.getData());
                            }
                        } catch (Exception e) {
                            Log.e("Error","Lỗi khi xử lý dữ liệu trả về từ GraphQL: " + e.getMessage());
                        }
                    }
                } else {
                    Log.e("Error","Phản hồi thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Error","Lỗi khi tải dữ liệu: " + t.getMessage());
            }
        });
    }
    private void addCart(int userId, int bookId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "mutation{\n" +
                "  addCart(userId: "+userId+",bookId: "+bookId+"){\n" +
                "    cartId\n" +
                "    userId\n" +
                "  }  \n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                        // Nếu có lỗi trong response, hiển thị lỗi
                        String errorMessage = data.getErrors().get(0).getMessage();
                        new AlertDialog.Builder(ProductDetail.this)
                                .setTitle("Thông báo")
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        try {
                            if (data.getData() instanceof LinkedTreeMap) {
                                LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                                Object  cart= dataMap.get("addCart");

                                if (cart instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> wishListMap = (LinkedTreeMap<String, Object>) cart;

                                    // Lấy và kiểm tra các trường từ map
                                    Double cartIdValue = (Double) wishListMap.get("cartId");
                                    Double userIdValue = (Double) wishListMap.get("userId");

                                    if (cartIdValue != null && userIdValue != null ) {
                                        int wishListId = cartIdValue.intValue();
                                        int userId = userIdValue.intValue();

                                        // Hiển thị thông báo thành công
                                        new AlertDialog.Builder(ProductDetail.this)
                                                .setTitle("Thông báo")
                                                .setMessage("Sách đã được thêm vào giỏ hàng!\n")
                                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                                .show();
                                    } else {
                                        Log.e("Error","Dữ liệu trả về chứa giá trị null cho cartId, userId");
                                    }
                                } else {
                                    Log.e("Error","Dữ liệu 'addCart' không phải LinkedTreeMap: " + cart);
                                }
                            } else {
                                Log.e("Error","Dữ liệu phản hồi không đúng định dạng: " + data.getData());
                            }
                        } catch (Exception e) {
                            Log.e("Error","Lỗi khi xử lý dữ liệu trả về từ GraphQL: " + e.getMessage());
                        }
                    }
                } else {
                    Log.e("Error","Phản hồi thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Error","Lỗi khi tải dữ liệu: " + t.getMessage());
            }
        });
    }

}
