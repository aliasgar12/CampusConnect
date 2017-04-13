package campusconnect.alias.com.campusconnect.web.services;

import campusconnect.alias.com.campusconnect.model.UserDetails;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alias on 4/12/2017.
 */

public interface LoginService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/";

    @POST("user/{login}")
    Call<UserDetails> loginUser(@Path("login") String login, @Body UserDetails user);


    class Factory {

        public static LoginService service;

        public static LoginService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(LoginService.class);
                return service;
            }
            return service;
        }
    }
}
