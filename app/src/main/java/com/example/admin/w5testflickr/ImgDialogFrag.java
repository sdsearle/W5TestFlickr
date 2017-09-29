package com.example.admin.w5testflickr;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by admin on 9/29/2017.
 */

public class ImgDialogFrag extends DialogFragment {

   public static ImgDialogFrag newInstance(String img) {

        Bundle args = new Bundle();
        args.putString("Image",img);
        ImgDialogFrag fragment = new ImgDialogFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.dialog_image, null);
        ImageView ivImg = view.findViewById(R.id.ivImg);
        String img = getArguments().getString("Image");
        Glide.with(this).load(img).into(ivImg);
        builder.setView(view);

        return builder.create();
    }
}
