package pl.sg.android_client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import pl.sg.android_client.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etpassword;
    private EditText etusername;
    private Button btnLogin;
    private Button btnBooks;

    private static String accessToken;

    private final String CLIENT_ID = "api-gateway";//""android-login";
    private final String CLIENT_SECRET = "636f4ef3-e6aa-4076-a5ae-5e4925524fed";//"35355373-f0bd-4462-bfe6-8dcfcd593b1f"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etpassword = findViewById(R.id.password);
        etusername = findViewById(R.id.username);

        btnLogin = findViewById(R.id.loginBtn);
        btnBooks = findViewById(R.id.booksBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccessToken();
            }
        });

        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBooksTest();
            }
        });

    }

    public void getAccessToken() {
        DataService dataService = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);

        String password = etpassword.getText().toString();
        String username = etusername.getText().toString();

        Call<AccessToken> call = dataService.getAccessToken(CLIENT_ID, "password",
                CLIENT_SECRET, "openid", username, password);

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    AccessToken token = response.body();
                    accessToken = token.getAccessToken();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getBooksTest() {
        DataService booksTestService = new Retrofit.Builder()
                .baseUrl("http://192.168.0.85:8082")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DataService.class);

        Call<String> call = booksTestService.getBooks();
        final String[] responseUrl = new String[1];

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    responseUrl[0] = response.raw().request().url().toString();
                } else {
                    responseUrl[0] = response.raw().request().url().toString();
                    loginTest(responseUrl[0]);
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loginTest(String url) {
        String password = etpassword.getText().toString();
        String username = etusername.getText().toString();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        String responseTypeParam = "response_type=";
        String clientIdParam = "client_id=";
        String scopeParam = "scope=";
        String stateParam = "state=";
        String redirectUriParam = "redirect_uri=";
        String nonceParam = "nonce=";

        String responseType = url.substring(url.indexOf(responseTypeParam) + responseTypeParam.length(), url.indexOf('&', url.indexOf(responseTypeParam)));
        String clientId = url.substring(url.indexOf(clientIdParam) + clientIdParam.length(), url.indexOf('&', url.indexOf(clientIdParam)));
        String scope = url.substring(url.indexOf(scopeParam) + scopeParam.length(), url.indexOf('&', url.indexOf(scopeParam)));
        String state = url.substring(url.indexOf(stateParam) + stateParam.length(), url.indexOf('&', url.indexOf(stateParam)));
        String redirectUri = url.substring(url.indexOf(redirectUriParam) + redirectUriParam.length(), url.indexOf('&', url.indexOf(redirectUriParam)));
        String nonce = url.substring(url.indexOf(nonceParam) + nonceParam.length());

        String baseUrl = url.substring(0, url.indexOf("auth?"));

        DataService booksTestService2 = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DataService.class);

        Call<String> call2 = booksTestService2
                .testLogin(responseType, clientId, scope, state, redirectUri, nonce);

        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("test");
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static String getAccessTokenString() {
        return accessToken;
    }

}
