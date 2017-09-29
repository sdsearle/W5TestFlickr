package com.example.admin.w5testflickr;

import com.example.admin.w5testflickr.model.FlickrResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by admin on 9/29/2017.
 */

public class RetrofitHelper {

    public static final String BASE_URL = "http://api.flickr.com/";

    public static Retrofit create(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Observable<Response<FlickrResponse>>createFlickrResponse(){
        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService.getFlickrResponse();
    }

    public interface ApiService{

        @GET("services/feeds/photos_public.gne?tag=kitten&format=json&nojsoncallback=1")
        Observable<Response<FlickrResponse>> getFlickrResponse();
    }

}
