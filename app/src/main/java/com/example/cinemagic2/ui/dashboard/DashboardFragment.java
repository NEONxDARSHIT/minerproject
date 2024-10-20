package com.example.cinemagic2.ui.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.cinemagic2.R;
import com.example.cinemagic2.databinding.FragmentDashboardBinding;

import java.util.List;


public class DashboardFragment extends Fragment {


    private FragmentDashboardBinding binding;
    private CartManager cartManager;
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        cartManager = CartManager.getInstance();
        List<CartItem> cartItems = cartManager.getCartItems();

        CartAdapter cartAdapter = new CartAdapter(getContext(),cartItems);
        recyclerView.setAdapter(cartAdapter);




        return root;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}