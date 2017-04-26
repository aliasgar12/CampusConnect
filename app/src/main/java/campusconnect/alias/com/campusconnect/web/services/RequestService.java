package campusconnect.alias.com.campusconnect.web.services;

import java.util.ArrayList;
import java.util.List;

import campusconnect.alias.com.campusconnect.model.Module;
import campusconnect.alias.com.campusconnect.model.Request;
import campusconnect.alias.com.campusconnect.model.Subject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alias on 4/23/2017.
 */

public interface RequestService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/";

    @POST("{userId}/request")
    Call<Void> addRequest(@Path("userId") int userId, @Body Request request);


    @GET("{userId}/request/sent")
    Call<ArrayList<Request>> getSentRequest(@Path("userId") int userId);

    @GET("{userId}/request/received")
    Call<ArrayList<Request>> getReceivedRequest(@Path("userId") int userId);

    @HTTP(method = "DELETE", path = "{userId}/request", hasBody = true)
    Call<Void> deleteRequest (@Path("userId") int id, @Body Request request);


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
