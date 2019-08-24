package com.kyd3snik.surfmemes.repositories;

import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemesRepository {
    public static void getMemes(final OnLoadedMemesListener listener) {
        NetworkService.getInstance().getMemeApi().getMemes().enqueue(new Callback<List<Meme>>() {
            @Override
            public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
                if (response.isSuccessful())
                    listener.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Meme>> call, Throwable t) {
                listener.OnError("Network fault");
            }
        });
    }

    public interface OnLoadedMemesListener {
        void OnSuccess(List<Meme> memes);

        void OnError(String errorMsg);
    }
}
