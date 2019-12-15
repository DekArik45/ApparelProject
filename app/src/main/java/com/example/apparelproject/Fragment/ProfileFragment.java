package com.example.apparelproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

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
    ConstraintLayout rootView;
    SharedPreferences sharedpreferences;
    Boolean session;
    String nama, email, alamat, username, password, hak_akses, jenis_kelamin;
    TextView profileNama, profileEmail, mAlamat, mUsername, mJenisKelamin, mHakAkses;
    Bitmap fotoBitmap;

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
        rootView = (ConstraintLayout) inflater.inflate(R.layout.fragment_profile, container, false);
//        btnPendingShipment = rootView.findViewById(R.id.profile_pending_shipment);
//        btnPendingPayment = rootView.findViewById(R.id.profile_pending_payment);
//        btnFinished = rootView.findViewById(R.id.profile_finished_order);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(Config.SESSION, false);

        nama = sharedpreferences.getString(Config.COLUMN_USER_NAMA, null);
        email = sharedpreferences.getString(Config.COLUMN_USER_EMAIL, null);
        username = sharedpreferences.getString(Config.COLUMN_USER_USERNAME, null);
        password = sharedpreferences.getString(Config.COLUMN_USER_PASSWORD, null);
        jenis_kelamin = sharedpreferences.getString(Config.COLUMN_USER_JENISKELAMIN, null);
        alamat = sharedpreferences.getString(Config.COLUMN_USER_ALAMAT, null);
        hak_akses = sharedpreferences.getString(Config.COLUMN_USER_HAKAKSES, null);

        byte[] fotoByte = Base64.decode(sharedpreferences.getString(Config.COLUMN_USER_IMAGE,null),Base64.DEFAULT);
        fotoBitmap = DbBitmapUtility.getImage(fotoByte);
        profileImage = rootView.findViewById(R.id.profile_foto);

        profileNama = rootView.findViewById(R.id.nama_profil);
        profileEmail = rootView.findViewById(R.id.email_profil);
        mAlamat = rootView.findViewById(R.id.alamat_profil);
        mJenisKelamin = rootView.findViewById(R.id.jeniskelamin_profil);
        mHakAkses = rootView.findViewById(R.id.hakakses_profil);
        mUsername = rootView.findViewById(R.id.user_profil);

        profileNama.setText(nama);
        profileEmail.setText(email);
        mAlamat.setText(alamat);
        mJenisKelamin.setText(jenis_kelamin);
        mUsername.setText(username);
        mHakAkses.setText(hak_akses);

        Glide.with(getContext())
                .load(fotoBitmap)
                .apply(new RequestOptions())
                .into(profileImage);


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
