package com.example.appsellbook.DTOs;

import java.util.ArrayList;

public class DataCache {
    private static ArrayList<CartDetail> selectedItems;
    private static double totalOrder;

    public static ArrayList<CartDetail> getSelectedItems() {
        return selectedItems;
    }

    public static void setSelectedItems(ArrayList<CartDetail> items) {
        selectedItems = items;
    }

    public static double getTotalOrder() {
        return totalOrder;
    }

    public static void setTotalOrder(double total) {
        totalOrder = total;
    }
    public static int getSelectedItemsCount() {
        return selectedItems != null ? selectedItems.size() : 0;
    }
}