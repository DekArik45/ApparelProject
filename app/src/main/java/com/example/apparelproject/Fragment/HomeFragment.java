package com.example.apparelproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.apparelproject.ProductCategoryActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.adapter.ItemProductMainAdapter;
import com.example.apparelproject.constants.Fields;
import com.example.apparelproject.data.ProductData;
import com.example.apparelproject.model.ProductModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView mRecyclerProduct;
    CircleImageView mTshirt, mCoats, mJersey, mUndershirt, mPants, mSweaters;
    FrameLayout rootView;
    private ArrayList<ProductModel> list = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);
        mCoats = rootView.findViewById(R.id.home_category_coats);
        mJersey = rootView.findViewById(R.id.home_category_jersey);
        mPants = rootView.findViewById(R.id.home_category_pants);
        mSweaters = rootView.findViewById(R.id.home_category_sweaters);
        mTshirt = rootView.findViewById(R.id.home_category_tshirt);
        mUndershirt = rootView.findViewById(R.id.home_category_undershirt);

        mRecyclerProduct = rootView.findViewById(R.id.home_recyclerview_product_favorite);

        settingCategory();

        settingDataListFavorite();

        return rootView;
//        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void settingCategory(){
        mCoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProductCategoryActivity.class);
                i.putExtra("category",Fields.COATS);
                startActivity(i);
            }
        });

        mJersey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProductCategoryActivity.class);
                i.putExtra("category",Fields.JERSEY);
                startActivity(i);
            }
        });

        mPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProductCategoryActivity.class);
                i.putExtra("category",Fields.PANTS);
                startActivity(i);
            }
        });

        mSweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProductCategoryActivity.class);
                i.putExtra("category",Fields.SWEATERS);
                startActivity(i);
            }
        });

        mTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProductCategoryActivity.class);
                i.putExtra("category",Fields.TSHIRT);
                startActivity(i);
            }
        });

        mUndershirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProductCategoryActivity.class);
                i.putExtra("category",Fields.UNDERSHIRT);
                startActivity(i);
            }
        });
    }

    public void settingDataListFavorite(){
        list.addAll(ProductData.getListData());
        mRecyclerProduct.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
        ItemProductMainAdapter productAdapter = new ItemProductMainAdapter(getContext());
        productAdapter.setListProduct(list);
        mRecyclerProduct.setAdapter(productAdapter);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
