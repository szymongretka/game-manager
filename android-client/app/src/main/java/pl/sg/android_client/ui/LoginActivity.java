package pl.sg.android_client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.sg.android_client.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etpassword;
    private EditText etusername;
    private Button btnLogin;

    private final String CLIENT_ID = "android-login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etpassword = findViewById(R.id.password);
        etusername = findViewById(R.id.username);
        btnLogin = findViewById(R.id.loginBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccessToken();
            }
        });
    }

    public void getAccessToken() {
        DataService dataService = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);

        String password = etpassword.getText().toString();
        String username = etusername.getText().toString();

        Call<AccessToken> call = dataService.getAccessToken(CLIENT_ID, "password",
                "35355373-f0bd-4462-bfe6-8dcfcd593b1f", "openid", username, password);

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    AccessToken accessToken = response.body();
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

}
