package com.example.apparelproject.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparelproject.MainActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.TransaksiAdminActivity;
import com.example.apparelproject.TransaksiCustomerActivity;
import com.example.apparelproject.adapter.ItemProductCartAdapter;

import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mehdi.sakout.fancybuttons.FancyButton;

public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecycler;
    FancyButton mBtn;
    FrameLayout rootView;
    private ArrayList<TransactionModel> list = new ArrayList<>();
    private TransactionQuery transactionQuery;
    private ItemProductCartAdapter itemProductCartAdapter;
    TextView mTotal;
    private SharedPreferences mSharedPreferencesLogin;
    Boolean session = false;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_cart, container, false);
        mSharedPreferencesLogin = getActivity().getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        session = mSharedPreferencesLogin.getBoolean(Config.SESSION,false);
        mTotal = rootView.findViewById(R.id.cart_total_bayar);
        mRecycler = rootView.findViewById(R.id.cart_recyclerView);
        mBtn = rootView.findViewById(R.id.checkout_button);

        transactionQuery = new TransactionQuery(getContext());

        list.clear();
        settingRecycler();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanCart();
            }
        });
        return rootView;
    }

    private void simpanCart(){
        long count = 0;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy H:m");
        String currentTime = df.format(c);

        for (int i=0;i<list.size();i++){
            list.get(i).setStatus(Config.STATUS_TRX_BELUMBAYAR);
            list.get(i).setTanggal(currentTime);
            count = transactionQuery.updateTransaction(list.get(i));
        }
        if (count > 0 ){
            SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
            String hakAkses = mSharedPreferences.getString(Config.COLUMN_USER_HAKAKSES,"");

            Toast.makeText(getContext(), "Check Out Success", Toast.LENGTH_LONG).show();
            if (hakAkses.equals("Admin")){
                Intent intent = new Intent(getContext(), TransaksiAdminActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getContext(), TransaksiCustomerActivity.class);
                startActivity(intent);
            }

        }
    }

    int grandTotal = 0;
    public void settingRecycler(){
        list.clear();
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        String namaUser = mSharedPreferences.getString(Config.COLUMN_USER_NAMA,"");
        list.addAll(transactionQuery.getAllTransactionCart(namaUser));

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false){
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);

                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    // this avoids trying to handle un-needed calls
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                grandTotal = itemProductCartAdapter.getTotal();
                Log.e("total","dd"+grandTotal);
                mTotal.setText("Rp."+String.valueOf(grandTotal));
            }
        });
        itemProductCartAdapter = new ItemProductCartAdapter(getContext());
        itemProductCartAdapter.setListTransaction(list);

        itemProductCartAdapter.setOnAddListener(new ItemProductCartAdapter.OnAddListener() {
            @Override
            public void onAdd(int position, int harga) {
                grandTotal += harga;
                itemProductCartAdapter.setTotal(grandTotal);

                grandTotal = itemProductCartAdapter.getTotal();
                Log.e("total","dd"+grandTotal);
                mTotal.setText("Rp."+String.valueOf(grandTotal));
            }
        });

        itemProductCartAdapter.setOnMinListener(new ItemProductCartAdapter.OnMinListener() {
            @Override
            public void onMin(int position, int harga, int jumlah) {

                grandTotal -= harga;
                itemProductCartAdapter.setTotal(grandTotal);

                grandTotal = itemProductCartAdapter.getTotal();
                Log.e("total","dd"+grandTotal);
                mTotal.setText("Rp."+String.valueOf(grandTotal));

                if (jumlah == 0){
                    CartFragment fg = new CartFragment();
//                    fg.setArguments();

                    getFragmentManager()  // or getSupportFragmentManager() if your fragment is part of support library
                            .beginTransaction()
                            .replace(R.id.fragment_cart, fg)
                            .commit();
                }
            }
        });
        mRecycler.setAdapter(itemProductCartAdapter);

    }
}
