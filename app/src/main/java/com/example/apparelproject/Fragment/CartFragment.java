package com.example.apparelproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.apparelproject.MainActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.adapter.ItemProductCartAdapter;
import com.example.apparelproject.constants.Fields;
import com.example.apparelproject.data.ProductData;
import com.example.apparelproject.model.ProductModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    private ArrayList<ProductModel> list = new ArrayList<>();
    private ArrayList<ProductModel> listData = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private SharedPreferences mSharedPreferences, mSharedPreferencesLogin;
    Boolean session = false;

    //Set the values
    Set<String> listCartNama = new HashSet<String>();
    Set<String> listCartPrice = new HashSet<String>();
    Set<String> listCartFoto = new HashSet<String>();

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

        mSharedPreferences = getActivity().getSharedPreferences(Fields.PRODUCT_PREFERENCE, Context.MODE_PRIVATE);

        mSharedPreferencesLogin = getActivity().getSharedPreferences(Fields.PREFERENCE, Context.MODE_PRIVATE);
        session = mSharedPreferencesLogin.getBoolean(Fields.SESSION_STATUS,false);

        listCartNama = mSharedPreferences.getStringSet(Fields.PRODUCT_NAME, null);
        listCartPrice = mSharedPreferences.getStringSet(Fields.PRODUCT_PRICE, null);
        listCartFoto = mSharedPreferences.getStringSet(Fields.PRODUCT_FOTO, null);

        mRecycler = rootView.findViewById(R.id.cart_recyclerView);
        mBtn = rootView.findViewById(R.id.checkout_button);
        list.clear();
        settingRecycler();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session){

                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(getContext(), " Checkout Succed", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getContext(), " Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    public void settingRecycler(){
        this.setListData(ProductData.getListData());
        if (listCartNama != null || listCartFoto != null || listCartPrice != null){
            List<String> arrayListNama = new ArrayList<String>(listCartNama);
            List<String> arrayListPrice = new ArrayList<String>(listCartPrice);
            List<String> arrayListFoto = new ArrayList<String>(listCartFoto);

//            for (String temp : listCartNama) {
//                arrayListNama.add(temp);
//            }for (String temp : listCartFoto) {
//                arrayListFoto.add(temp);
//            }for (String temp : listCartPrice) {
//                arrayListPrice.add(temp);
//            }

            int j;
            for (j=0;j<1;j++){
                for (int i=0; i<getListData().size(); i++){
                    if (arrayListNama.get(j).equals(getListData().get(i).getNama()) && arrayListPrice.get(j).equals(getListData().get(i).getHarga()) && arrayListFoto.get(j).equals(getListData().get(i).getFoto())){
                        list.add(getListData().get(i));
                        Log.e("ada = ","1");
                    }
                }
            }

//        list.addAll(ProductData.getListData());
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        ItemProductCartAdapter productAdapter = new ItemProductCartAdapter(getContext());
        productAdapter.setListProduct(list);
        mRecycler.setAdapter(productAdapter);

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ArrayList<ProductModel> getListData() {
        return listData;
    }

    public void setListData(ArrayList<ProductModel> listData) {
        this.listData = listData;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
