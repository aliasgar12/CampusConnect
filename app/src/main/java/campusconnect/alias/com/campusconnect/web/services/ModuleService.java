package campusconnect.alias.com.campusconnect.web.services;

import java.util.HashSet;
import java.util.List;

import campusconnect.alias.com.campusconnect.model.Module;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alias on 4/13/2017.
 */

public interface ModuleService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/";

    @GET("{userId}/subject/{subjectId}")
    Call<HashSet<Module>> getModules(@Path("userId") int id, @Path("subjectId") int subId );


    class Factory {

        public static ModuleService service;

        public static ModuleService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(ModuleService.class);
                return service;
            }
            return service;
        }
    }
}