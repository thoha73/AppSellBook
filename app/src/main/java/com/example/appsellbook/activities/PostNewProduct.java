package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.DTOs.Author;
import com.example.appsellbook.DTOs.Category;
import com.example.appsellbook.R;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostNewProduct extends AppCompatActivity {
    int SELECT_PHOTO_BTN1 = 1 ;
    int SELECT_PHOTO_BTN2 = 2 ;
    int authorId,categoryId;
    ImageView img,img1,img2,img3;
    Button btn_selectedimage,btn_selectedimag1,btn_POST;
    Uri uri;
    EditText edtbookName,edtISBN,edtPrice,edtDescription,edtQuantity,edtPublisher;
    Spinner spinnerAuthor,spinnerCategory;
    String encodedImage="",encodedImage1="",encodedImage2="",encodedImage3="";
    ImageView[] imageViews;
    List<String> imageList = new ArrayList<>();
    private int currentImageIndex = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postproduct);
        btn_selectedimage = findViewById(R.id.btn_SelectedImage);
        btn_selectedimag1=findViewById(R.id.btn_SelectedImage1);
        img = findViewById(R.id.imgBookSelected);
        img1=findViewById(R.id.imgBookSelected1);
        img2=findViewById(R.id.imgBookSelected2);
        img3=findViewById(R.id.imgBookSelected3);
        btn_POST = findViewById(R.id.btn_Post);
        edtbookName = findViewById(R.id.editTextbookname);
        spinnerAuthor = findViewById(R.id.spn_author);
        spinnerCategory = findViewById(R.id.spn_category);
        edtISBN = findViewById(R.id.editTextISBN);
        edtPrice = findViewById(R.id.editTextPrice);
        edtDescription = findViewById(R.id.textView12);
        edtPublisher = findViewById(R.id.editTextPublisher);
        edtQuantity = findViewById(R.id.editTextQuantity);
        imageViews= new ImageView[]{img3,img2,img1};
        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setSelectedItemId(R.id.menu_product);
        GetAuthor();
        GetCategory();
        bottom_NavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), OwnerNotification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), ShopOwner.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_product){
                    return  true;
                }
                return false;
            }
        });
        btn_selectedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO_BTN1);


            }
        });
        btn_selectedimag1.setOnClickListener(v->{
            if (currentImageIndex < imageViews.length) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO_BTN2);
            } else {
                Toast.makeText(PostNewProduct.this, "Đã chọn đủ ảnh!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_POST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if required fields are empty
                if (edtbookName.getText().toString().isEmpty() ||
                        edtDescription.getText().toString().isEmpty() ||
                        edtISBN.getText().toString().isEmpty() ||
                        edtPrice.getText().toString().isEmpty()) {

                    new AlertDialog.Builder(PostNewProduct.this)
                            .setTitle("Thông báo")
                            .setMessage("Vui lòng nhập đầy đủ thông tin")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();
                } else{
                    // Proceed only if the URI is not null and bookName is not empty
                    int price = Integer.parseInt(edtPrice.getText().toString());
                    int quantity=Integer.parseInt(edtQuantity.getText().toString());
                    ArrayList<String> arrayList=(ArrayList<String>) imageList;
                    CreateBook(authorId, categoryId, edtbookName.getText().toString(), edtISBN.getText().toString(), price, price,quantity , edtPublisher.getText().toString(), edtDescription.getText().toString(),arrayList);
                }
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                // Nén bitmap thành Base64
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                imageList.add(encodedImage);
                // Gán ảnh vào ImageView tùy theo requestCode
                if (requestCode == SELECT_PHOTO_BTN1) {
                    img.setImageBitmap(bitmap); // `img` là ImageView của nút btn_selectedimage
                } else if (requestCode == SELECT_PHOTO_BTN2) {
                    if (requestCode == SELECT_PHOTO_BTN2) {
                        // Gán ảnh vào ImageView tiếp theo trong danh sách
                        if (currentImageIndex < imageViews.length) {
                            imageViews[currentImageIndex].setImageBitmap(bitmap);
                            currentImageIndex++; // Tăng chỉ số để cập nhật ảnh tiếp theo
                        }
                        if(currentImageIndex>3){
                            btn_selectedimag1.setVisibility(View.GONE);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Không thể load ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GetAuthor() {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query {\n" +
                "  authors {\n" +
                "    authorId\n" +
                "    authorName\n" +
                "  }\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);

        Log.d("GraphQL", "Request: " + request.getQuery());

        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data != null && data.getData() instanceof Map) {
                        Map<String, Object> responseData = (Map<String, Object>) data.getData();
                        Object authorData = responseData.get("authors");

                        if (authorData instanceof List) {
                            List<Map<String, Object>> authorList = (List<Map<String, Object>>) authorData;
                            List<Author> authors = new ArrayList<>();

                            for (Map<String, Object> authorMap : authorList) {
                                Author author = new Author();
                                author.setAuthorId((int) ((Double) authorMap.get("authorId")).doubleValue());
                                author.setAuthorName((String) authorMap.get("authorName"));
                                authors.add(author);
                            }

                            // Gắn dữ liệu vào Spinner
                            if (!authors.isEmpty()) {
                                ArrayAdapter<Author> adapter = new ArrayAdapter<>(
                                        PostNewProduct.this,
                                        android.R.layout.simple_spinner_item,
                                        authors
                                );
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerAuthor.setAdapter(adapter);

                                // Xử lý sự kiện chọn tác giả
                                spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Author selectedAuthor = (Author) parent.getItemAtPosition(position);
                                        authorId = selectedAuthor.getAuthorId();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // Không có mục nào được chọn
                                    }
                                });
                            } else {
                                Log.d("GraphQL", "Danh sách tác giả không có dữ liệu.");
                            }
                        } else {
                            Log.d("GraphQL", "Không thể lấy danh sách tác giả từ dữ liệu trả về.");
                        }
                    } else {
                        Log.d("GraphQL", "Dữ liệu trả về không phải là Map.");
                    }
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", t.getMessage());
            }
        });
    }

    private void GetCategory() {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);

        String query = "query {\n" +
                "  categories {\n" +
                "    categoryId\n" +
                "    categoryName\n" +
                "  }\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        Log.d("GraphQL", "Request: " + request.getQuery());

        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();

                    if (data != null && data.getData() instanceof Map) {
                        Map<String, Object> responseData = (Map<String, Object>) data.getData();
                        Object categoryData = responseData.get("categories");

                        if (categoryData instanceof List) {
                            List<Map<String, Object>> categoryList = (List<Map<String, Object>>) categoryData;
                            List<Category> categories = new ArrayList<>();

                            for (Map<String, Object> categoryMap : categoryList) {
                                Category category = new Category();
                                category.setCategoryId((int) ((Double) categoryMap.get("categoryId")).doubleValue());
                                category.setCategoryName((String) categoryMap.get("categoryName"));
                                categories.add(category);
                            }

                            // Gắn dữ liệu vào Spinner
                            if (!categories.isEmpty()) {
                                ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                                        PostNewProduct.this,
                                        android.R.layout.simple_spinner_item,
                                        categories
                                );
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCategory.setAdapter(adapter);

                                // Xử lý sự kiện chọn danh mục
                                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Category selectedCategory = (Category) parent.getItemAtPosition(position);
                                        categoryId = selectedCategory.getCategoryId();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // Không có mục nào được chọn
                                    }
                                });
                            } else {
                                Log.d("GraphQL", "Danh sách danh mục không có dữ liệu.");
                            }
                        } else {
                            Log.d("GraphQL", "Không thể lấy danh sách danh mục từ dữ liệu trả về.");
                        }
                    } else {
                        Log.d("GraphQL", "Dữ liệu trả về không phải là Map.");
                    }
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", t.getMessage());
            }
        });
    }


    private void CreateBook(int authorId,int categoryIds,String tenSach, String isbn, int listedPrice, int sellPrice, int quantity, String publisher, String description,ArrayList<String> list) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "            mutation {\n" +
                "                createBook(authorId: "+authorId+",categoryIds: ["+categoryIds+"],bookTypeInput: {\n" +
                "                  bookId: 0,\n" +
                "                    bookName: \""+tenSach+"\",\n" +
                "                    isbn: \""+isbn+"\",\n" +
                "                    listedPrice: "+listedPrice+",\n" +
                "                    sellPrice: "+sellPrice+",\n" +
                "                    quantity: "+quantity+",\n" +
                "                    publisher: \""+publisher+"\"\n" +
                "                    description: \""+description+"\",\n" +
                "                    rank: 5,\n" +
                "                    images: [\n" +
                "                {\n" +
                "                  imageId: 0\n" +
                "                    imageName: \"avatar\",\n" +
                "                    imageData: \""+list.get(0)+"\",  \n" +
                "                    icon: true,\n" +
                "                    bookId: 1\n" +
                "                },\n" +
                "                {\n" +
                "                  imageId: 1\n" +
                "                    imageName: \"img1\",\n" +
                "                    imageData: \""+list.get(1)+"\",  \n" +
                "                    icon: false,\n" +
                "                    bookId: 1\n" +
                "                },\n" +
                "                {\n" +
                "                    imageId:2 \n" +
                "                    imageName: \"img2\",\n" +
                "                    imageData: \""+list.get(2)+"\",  \n" +
                "                    icon: false,\n" +
                "                    bookId: 1\n" +
                "                },\n" +
                "                {\n" +
                "                  imageId: 3\n" +
                "                    imageName: \"img3\",\n" +
                "                    imageData: \""+list.get(3)+"\",  \n" +
                "                    icon: false,\n" +
                "                    bookId: 1\n" +
                "                }\n" +
                "],\n" +
                "                \n" +
                "                }) {\n" +
                "                    bookId\n" +
                "                }\n" +
                "            }";
        GraphQLRequest request = new GraphQLRequest(query);
        Log.d("GraphQL", "Request: " + request.getQuery());
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful()) {
                    GraphQLResponse<Object> data = response.body();
                    if (data != null && data.getData() instanceof Map) {
                        Map<String, Object> responseData = (Map<String, Object>) data.getData();
                        // Log the whole response for debugging
                        Log.d("GraphQL", "Response Data: " + responseData);
                        Object createBookData = responseData.get("createBook");
                        if (createBookData instanceof Map) {
                            // Extract the bookId from the createBook data
                            Map<String, Object> createBookMap = (Map<String, Object>) createBookData;
                            Object bookId = createBookMap.get("bookId");

                            if (bookId != null) {
                                // Book creation was successful
                                new AlertDialog.Builder(PostNewProduct.this)
                                        .setTitle("Thông báo")
                                        .setMessage("Thêm sản phẩm thành công!")
                                        .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss())
                                        .create()
                                        .show();
                            } else {
                                Log.d("GraphQL", "bookId không có trong dữ liệu trả về.");
                            }
                        } else {
                            Log.d("GraphQL", "createBook không phải là Map.");
                        }
                    } else {
                        Log.d("GraphQL", "Dữ liệu trả về không phải là Map hoặc là null.");
                    }
                } else {
                    // Handle failure cases
                    Log.d("Error", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", t.getMessage());
            }
        });
    }

}