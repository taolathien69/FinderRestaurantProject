package com.example.nhom4_cuoiki;

import java.util.Comparator;

public class Place {
    public String name;
    public String vicinity;
    public double rating;
    // Các trường khác...
    public double distance; // Thêm trường khoảng cách
    public String photoUrl;
    public static Comparator<Place> RatingComparator = new Comparator<Place>() {
        @Override
        public int compare(Place place1, Place place2) {
            // Sắp xếp giảm dần (desc) theo độ đánh giá
            return Double.compare(place2.rating, place1.rating);
        }
    };
}
