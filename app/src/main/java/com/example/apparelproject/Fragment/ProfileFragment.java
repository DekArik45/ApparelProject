package com.example.apparelproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.FinishedOrderActivity;
import com.example.apparelproject.LoginActivity;
import com.example.apparelproject.PendingPaymentActivity;
import com.example.apparelproject.PendingShipmentActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.constants.Fields;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnPendingShipment,btnPendingPayment, btnFinished;
    CircleImageView profileImage;
    FrameLayout rootView;
    SharedPreferences sharedpreferences;
    Boolean session;
    String nama, email, foto, username, password;
    TextView profileNama, profileEmail;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_profile, container, false);
//        btnPendingShipment = rootView.findViewById(R.id.profile_pending_shipment);
//        btnPendingPayment = rootView.findViewById(R.id.profile_pending_payment);
//        btnFinished = rootView.findViewById(R.id.profile_finished_order);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences(Fields.PREFERENCE, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(Fields.SESSION_STATUS, false);

        if (!session) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }

        nama = sharedpreferences.getString(Fields.NAME, null);
        email = sharedpreferences.getString(Fields.EMAIL, null);
        foto = sharedpreferences.getString(Fields.FOTO, null);
        username = sharedpreferences.getString(Fields.USERNAME, null);
        password = sharedpreferences.getString(Fields.PASSWORD, null);

//        profileEmail = rootView.findViewById(R.id.profile_email);
//        profileNama = rootView.findViewById(R.id.profile_nama);
        profileImage = rootView.findViewById(R.id.profile_foto);

        profileNama.setText(nama);
        profileEmail.setText(email);

        Glide.with(getContext())
                .load(foto)
                .apply(new RequestOptions())
                .into(profileImage);

        btnPendingPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PendingPaymentActivity.class);
                startActivity(intent);
            }
        });
        btnPendingShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PendingShipmentActivity.class);
                startActivity(intent);
            }
        });
        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), FinishedOrderActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
//        return inflater.inflate(R.layout.fragment_profile, container, false);
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
