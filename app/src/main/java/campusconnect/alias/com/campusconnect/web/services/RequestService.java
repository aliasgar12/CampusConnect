package campusconnect.alias.com.campusconnect.web.services;

import campusconnect.alias.com.campusconnect.model.Module;
import campusconnect.alias.com.campusconnect.model.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alias on 4/23/2017.
 */

public interface RequestService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/";

    @POST("{userId}/request")
    Call<Void> addRequest(@Path("userId") int userId, @Body Request request);

    class Factory{

        private static RequestService service;

        public static RequestService getInstance(){
            if(service == null){
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(RequestService.class);
                return service;

            }
            return service;
        }
    }
}
