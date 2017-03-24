package choongyul.android.com.memowithnodejs;

import choongyul.android.com.memowithnodejs.domain.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by myPC on 2017-03-24.
 */

public interface LocalhostInterface {

    // 중에서 포트 이하 부분을 get 이하에 쓴다.
//    @GET("566d677961726f6331397471525a50/json/SearchParkingInfo/1/10/{gu}")
    @GET("post")
    Call<Data> getData(); // path는 리스트 리포함수를 통해서 데이터를 가져오게되는데 거기 들어오는 갑승ㄹ path를 통해 url을 세팅한다.
    // {gu} 부분을 설정하는 String user를 가져온다.


}
