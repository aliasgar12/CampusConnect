package campusconnect.alias.com.campusconnect.web.services;

import java.util.List;

import campusconnect.alias.com.campusconnect.model.Subject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * Created by alias on 4/19/2017.
 */

public interface SearchService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/";


    // Get subjects for a particular userId
    @GET("{userId}/subject")
    Call<List<Subject>> get(@Path("userId") int id);

    // add a subject to a users profile
//    @POST("{userId}/subject")
//    Call<Void> addSubject (@PathParam("userId") int id, Subject subject);





    @HTTP(method = "DELETE", path = "{userId}/subject", hasBody = true)
    Call<Void> deleteSubject (@Path("userId") int id, @Body Subject subject);

    class Factory {

        public static SubjectService service;

        public static SubjectService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(SubjectService.class);
                return service;
            }
            return service;
        }
    }
}
