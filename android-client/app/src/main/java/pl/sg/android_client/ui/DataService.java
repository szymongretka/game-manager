package pl.sg.android_client.ui;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @FormUrlEncoded
    @POST("/auth/realms/GameManager/protocol/openid-connect/token")
    Call<AccessToken> getAccessToken(@Field("client_id") String client_id,
                                     @Field("grant_type") String grant_type,
                                     @Field("client_secret") String client_secret,
                                     @Field("scope") String scope,
                                     @Field("username") String username,
                                     @Field("password") String password);


    @GET("/books")
    Call<String> getBooks(@Header("Cookie") String SESSION);

    @Headers("Content-Type: application/json")
    @GET("/data/test")
    Call<String> getDataTest(@Header("Authorization") String authHeader);

//    @FormUrlEncoded
    @GET("auth")
    Call<String> testLogin(@Query(value = "response_type") String response_type,
                           @Query(value = "client_id") String client_id,
                           @Query(value = "scope") String scope,
                           @Query(value = "state") String state,
                           @Query(value = "redirect_uri") String redirect_uri,
                           @Query(value = "nonce") String nonce);
//                           @Field("username") String username,
//                           @Field("password") String password);

}
