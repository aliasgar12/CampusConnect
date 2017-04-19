package campusconnect.alias.com.campusconnect.web.services;

import java.util.List;

import campusconnect.alias.com.campusconnect.model.Subject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by alias on 4/19/2017.
 */

public interface SearchService {

    String BASE_URL = "http://10.0.2.2:8080/StudyConnect/webapi/user/search/";

    // Get subject by Subject CRN
    @GET("{subjectCRN}")
    Call<Subject> getSubjectByCRN(@Path("subjectCRN") int subCRN);

    // Get subject by College
    @GET("college/{collegeId}")
    Call<List<Subject>> getSubjectByCollege(@Path("collegeId") int collegeId);

    // Get subject by College and Department
    @GET("college/{collegeId}/dept/{deptId}")
    Call<Subject> getSubjectByCollegeDept(@Path("collegeId") int collegeId, @Path("deptId") int deptId);




    class Factory {

        public static SearchService service;

        public static SearchService getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(SearchService.class);
                return service;
            }
            return service;
        }
    }
}
