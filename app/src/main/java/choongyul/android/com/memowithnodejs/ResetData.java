package choongyul.android.com.memowithnodejs;

import android.content.Intent;
import android.util.Log;

import choongyul.android.com.memowithnodejs.domain.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static choongyul.android.com.memowithnodejs.MainActivity.SITE_URL;

/**
 * Created by myPC on 2017-03-24.
 */

public class ResetData {

    private static ResetData instance = null;
    private ResetData() { }
    public static ResetData getInstance () {
        if(instance == null) {
            instance = new ResetData();
        }
        return instance;
    }

    public static void getData(){
        // 1. 레트로핏을 생성하고
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SITE_URL) // 포트까지가 베이스url이다.
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2. 사용할 인터페이스를 설정한다.
        LocalhostInterface localhost = retrofit.create(LocalhostInterface.class);
        // 3. 데이터를 가져온다
        Call<Data> result = localhost.getData();

        result.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                // 값이 정상적으로 리턴되었을 경우
                if(response.isSuccessful()) {
                    Data data = response.body(); // 원래 반환 값인 jsonString이 Data 클래스로 변환되어 리턴된다.
                    DataStore dataStore = DataStore.getInstance();
                    dataStore.setDatas(data.getData());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    //정상적이지 않을 경우 message에 오류내용이 담겨 온다.
                    Log.e("onResponse","값이 비정상적으로 리턴되었다. = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
}
