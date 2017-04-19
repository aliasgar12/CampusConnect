package campusconnect.alias.com.campusconnect.web.services;

import java.util.List;
import campusconnect.alias.com.campusconnect.model.College;
import campusconnect.alias.com.campusconnect.model.Department;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by alias on 4/18/2017.
 */

public interface AddCourseService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/";

    @GET("college")
    Call<List<College>> getCollegeList();

    @GET("dept")
    Call<List<Department>> getDeptList();

    class Factory {

        public static AddCourseService service;

        public static AddCourseService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(AddCourseService.class);
                return service;
            }
            return service;
        }
    }
}
