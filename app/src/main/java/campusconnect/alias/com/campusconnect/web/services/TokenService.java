package campusconnect.alias.com.campusconnect.web.services;


import campusconnect.alias.com.campusconnect.model.UserDetails;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by alias on 4/29/2017.
 */

public interface TokenService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/token/";


    // adding user token
    @PUT("addToken")
    Call<Void>  setToken (@Body UserDetails userDetails);

    @PUT("deleteToken")
    Call<Void>  deleteToken (@Body UserDetails userDetails);



    class Factory {

        public static TokenService service;

        public static TokenService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(TokenService.class);
                return service;
            }
            return service;
        }
    }


}
