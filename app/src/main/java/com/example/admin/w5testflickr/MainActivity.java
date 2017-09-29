package com.example.admin.w5testflickr;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.w5testflickr.model.FlickrResponse;
import com.example.admin.w5testflickr.model.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FlickrResponseRecyclerViewAdapter.OnListInteractionListener{
    private static final String TAG = "TAG";
    List<Item> items = new ArrayList<>();

    Context context = this;

    Observer<Response<FlickrResponse>> flickrResponseObserver = new Observer<Response<FlickrResponse>>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull Response<FlickrResponse> flickrResponseResponse) {
            if(flickrResponseResponse.body()!= null) {
                items = flickrResponseResponse.body().getItems();
            }else{
                Log.d(TAG, "onNext: " + flickrResponseResponse.raw().request().url().toString());
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {
            frrva.addData(items);
        }
    };
    private RecyclerView.Adapter adapter;
    private FlickrResponseRecyclerViewAdapter frrva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<Response<FlickrResponse>> flickrResponse = RetrofitHelper.createFlickrResponse();
        flickrResponse.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(flickrResponseObserver);

        RecyclerView rvFlickr = (RecyclerView) findViewById(R.id.rvFlickr);
        rvFlickr.setLayoutManager(new GridLayoutManager(this, 3));
        frrva = new FlickrResponseRecyclerViewAdapter(items, this, this);
        adapter = frrva;
        rvFlickr.setAdapter(adapter);

    }

    @Override
    public void onLongClick(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Display Image")
                .setPositiveButton("Small Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ImgDialogFrag imgDialogFrag = ImgDialogFrag.newInstance(item.getMedia().getM());

                        FragmentManager fm = getSupportFragmentManager();

                        imgDialogFrag.show(fm,"imgFrag");
                    }
                })
                .setNegativeButton("Large Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, ImageActivity.class);
                        intent.putExtra("Image" , item.getMedia().getM());
                        startActivity(intent);
                    }
                });

        builder.create().show();

    }
}
