package com.example.cuoiki_android_lythuyet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.cuoiki_android_lythuyet.ListKeeperSaved;
import com.example.cuoiki_android_lythuyet.PetListActivity;
import com.example.cuoiki_android_lythuyet.R;
import com.example.cuoiki_android_lythuyet.adapter.BookingAdapter;
import com.example.cuoiki_android_lythuyet.models.Booking;
import com.example.cuoiki_android_lythuyet.tag.Tag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RequestsFragment extends Fragment {
    private RecyclerView rcvBooking;
    private BookingAdapter bookingAdapter;
    private FloatingActionButton btn_createRequest;
    LinearLayout itemRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        rcvBooking = view.findViewById(R.id.rcvOwnerRequest);

        bookingAdapter = new BookingAdapter(getContext(), getListBooking());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvBooking.setLayoutManager(linearLayoutManager);
        rcvBooking.setAdapter(bookingAdapter);
        btn_createRequest = (FloatingActionButton) view.findViewById(R.id.btn_createRequest);
        btn_createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PetListActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private List<Booking> getListBooking(){
        List<Booking> listBookings = new ArrayList<>();
        listBookings.add(new Booking("Phuc", R.drawable.ahinhanhdemo, 70.0, 8,"10/12/2022 - 10.00"));
        listBookings.add(new Booking("Luc", R.drawable.ahinhanhdemo, 70.0, 8,"10/12/2022 - 10.00"));
        listBookings.add(new Booking("Quoc", R.drawable.ahinhanhdemo, 70.0, 8,"10/12/2022 - 10.00"));
        listBookings.add(new Booking("Ngan", R.drawable.ahinhanhdemo, 70.0, 8,"10/12/2022 - 10.00"));
        listBookings.add(new Booking("Manh", R.drawable.ahinhanhdemo, 70.0, 8,"10/12/2022 - 10.00"));

        return listBookings;
    }

    @Override
    public void onStart() {
        super.onStart();
        Tag.setTag("request");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bookingAdapter != null)
            bookingAdapter.release();
    }
}