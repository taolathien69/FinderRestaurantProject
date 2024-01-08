package com.example.nhom4_cuoiki;

//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.apptravel4.model.Place;
//import com.example.apptravel4.model.PlacesApiResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class ListRestaurantActivity extends AppCompatActivity {
//    private Button btnMap;
//    private List<Place> restaurantList = new ArrayList<>();
//    private RestaurantAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_listnhahang);
//        btnMap = findViewById(R.id.button11);
//
//        // Khởi tạo Adapter và ListView
//        adapter = new RestaurantAdapter(this, restaurantList);
//        ListView listView = findViewById(R.id.listView);
//        listView.setAdapter(adapter);
//
//        callPlacesApi();
//
//        btnMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListRestaurantActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void callPlacesApi() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://maps.googleapis.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PlacesApiService apiService = retrofit.create(PlacesApiService.class);
//
//        Call<PlacesApiResponse> call = apiService.getNearbyRestaurants("latitude,longitude", 1000, "restaurant", "AIzaSyDAnlXnGXzsSdvwBR2qx84jgXAC4JZ4p7E");
//
//        call.enqueue(new Callback<PlacesApiResponse>() {
//            @Override
//            public void onResponse(Call<PlacesApiResponse> call, Response<PlacesApiResponse> response) {
//                if (response.isSuccessful()) {
//                    List<Place> places = response.body().results;
//
//                    // Cập nhật dữ liệu trong Adapter và thông báo thay đổi
//                    adapter.clear();
//                    adapter.addAll(places);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.e("APIError", "Error: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlacesApiResponse> call, Throwable t) {
//                Log.e("NetworkError", "Error: " + t.getMessage());
//            }
//        });
//    }
//}

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListRestaurantActivity extends AppCompatActivity {
    private Button btnMap ,btnBack;
    private List<Place> restaurantList = new ArrayList<>();
    private RestaurantAdapter adapter;
    private static final int REQUEST_CODE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listnhahang);

        btnMap = findViewById(R.id.button11);

        btnBack =findViewById(R.id.buttonBack11);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến MapActivity khi nhấn nút "Xem vị trí"
                Intent intent = new Intent(ListRestaurantActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        // Khởi tạo Adapter và ListView
        adapter = new RestaurantAdapter(this, restaurantList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Kiểm tra và yêu cầu quyền truy cập vị trí nếu cần
        checkAndRequestLocationPermission();

        // Gọi API để lấy danh sách nhà hàng xung quanh vị trí hiện tại
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocationAndCallApi();
            }
        });
    }

    // Hàm kiểm tra và yêu cầu quyền truy cập vị trí (đối với Android 10+)
    private void checkAndRequestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, có thể thực hiện công việc liên quan đến vị trí ở đây.
            } else {
                // Chưa có quyền, yêu cầu quyền từ người dùng
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
            }
        }
    }

    // Hàm gọi API khi đã có quyền truy cập vị trí
    private void getCurrentLocationAndCallApi() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Lấy latitude và longitude từ vị trí hiện tại
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Gọi API với latitude và longitude
                            callPlacesApi(latitude, longitude);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý lỗi khi không thể lấy được vị trí
                        Log.e("LocationError", "Error getting location: " + e.getMessage());
                    }
                });
    }

    // Hàm gọi API để lấy danh sách nhà hàng xung quanh vị trí
    private void callPlacesApi(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlacesApiService apiService = retrofit.create(PlacesApiService.class);

        Call<PlacesApiResponse> call = apiService.getNearbyRestaurants(latitude + "," + longitude, 1000, "restaurant", "AIzaSyDAnlXnGXzsSdvwBR2qx84jgXAC4JZ4p7E");

        call.enqueue(new Callback<PlacesApiResponse>() {
            @Override
            public void onResponse(Call<PlacesApiResponse> call, Response<PlacesApiResponse> response) {
                if (response.isSuccessful()) {
                    List<Place> places = response.body().results;

                    // Lấy vị trí hiện tại
                    LatLng currentLocation = new LatLng(latitude, longitude);

                    // Lặp qua danh sách nhà hàng để tính khoảng cách
//                    for (Place place : places) {
//                        LatLng restaurantLocation = new LatLng(place.geometry.location.lat, place.geometry.location.lng);
//
//                        // Tính khoảng cách từ nhà hàng đến vị trí hiện tại
//                        double distance = SphericalUtil.computeDistanceBetween(currentLocation, restaurantLocation);
//
//                        // Cập nhật trường khoảng cách
//                        place.distance = distance;
//                    }

                    // Sắp xếp danh sách theo độ đánh giá (rating)
                    Collections.sort(places, Place.RatingComparator);

                    // Cập nhật dữ liệu trong Adapter và thông báo thay đổi
                    adapter.clear();
                    adapter.addAll(places);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("APIError", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PlacesApiResponse> call, Throwable t) {
                Log.e("NetworkError", "Error: " + t.getMessage());
            }
        });
    }

    // Xử lý kết quả yêu cầu quyền truy cập vị trí (đối với Android 10+)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, có thể thực hiện công việc liên quan đến vị trí ở đây.
            } else {
                // Người dùng từ chối cấp quyền, xử lý theo ý đồ của bạn (có thể thông báo lỗi hoặc yêu cầu lại quyền).
            }
        }
    }
}