package campusconnect.alias.com.campusconnect.web.services;

import campusconnect.alias.com.campusconnect.model.UserDetails;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Created by alias on 5/1/2017.
 */

public interface UpdateInfoService {
    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/";

    @PUT("update")
    Call<Void> updateUser( @Body UserDetails user);


    class Factory {

        public static UpdateInfoService service;

        public static UpdateInfoService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(UpdateInfoService.class);
                return service;
            }
            return service;
        }
    }
}
