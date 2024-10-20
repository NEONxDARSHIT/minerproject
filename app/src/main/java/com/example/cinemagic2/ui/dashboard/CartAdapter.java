package com.example.cinemagic2.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemagic2.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

// CartAdapter.java
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;

    public CartAdapter(Context context,List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.itemMovieTextView.setText(cartItem.getMoviename());
        holder.itemAddressTextView.setText(cartItem.getAddress());
        holder.itemDateTextView.setText(cartItem.getDate());
        holder.itemSeatsTextView.setText(cartItem.getSeats());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate show time and screen number
                String showTime = generateShowTime(); // Get current time
                int screenNumber = generateRandomScreenNumber(); // Get a random screen number

                // Combine details for the QR code content
                String qrContent = "Show time: " + showTime + ", Screen: " + screenNumber+ ", QR code is valid: Please welcome";

                // Generate and display the QR code along with the show time and screen number
                generateQRCode(qrContent, showTime, screenNumber);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemMovieTextView;
        TextView itemAddressTextView;
        TextView itemDateTextView;
        TextView itemSeatsTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemMovieTextView = itemView.findViewById(R.id.textViewMovieName);
            itemDateTextView=itemView.findViewById(R.id.textViewDate);
            itemAddressTextView=itemView.findViewById(R.id.textViewAddress);
            itemSeatsTextView=itemView.findViewById(R.id.textViewSeats);

        }

    }
    private String generateShowTime() {
        // Set Indian time zone
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
        Calendar calendar = Calendar.getInstance(timeZone);

        // Set the time range (7:00 AM to 11:50 PM)
        int startHour = 7;  // 7:00 AM
        int endHour = 23;   // 11:50 PM
        int randomHour = startHour + new Random().nextInt(endHour - startHour + 1);  // Random hour
        int randomMinute = new Random().nextInt(60);  // Random minute

        // Set the hour and minute in the calendar
        calendar.set(Calendar.HOUR_OF_DAY, randomHour);
        calendar.set(Calendar.MINUTE, randomMinute);

        // Format the time in "HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setTimeZone(timeZone);
        return sdf.format(calendar.getTime());
    }

    private int generateRandomScreenNumber() {
        Random random = new Random();
        return random.nextInt(7) + 1;
    }


    private void generateQRCode(String content, String showTime, int screenNumber) {
        try {

            int size = 500;


            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);


            showQRCodeDialog(bitmap, showTime, screenNumber);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    private void showQRCodeDialog(Bitmap qrBitmap, String showTime, int screenNumber) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.qr_code_dialog); // Use the custom dialog layout


        ImageView qrImageView = dialog.findViewById(R.id.qrImageView);
        TextView showTimeTextView = dialog.findViewById(R.id.showTimeTextView);
        TextView screenNumberTextView = dialog.findViewById(R.id.screenNumberTextView);


        qrImageView.setImageBitmap(qrBitmap);


        showTimeTextView.setText("Show time: " + showTime);
        screenNumberTextView.setText("Screen: " + screenNumber);


        dialog.show();
    }


}
