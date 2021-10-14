package pl.sg.android_client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.sg.android_client.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MenuActivity extends AppCompatActivity {

    private Button btnData;

    private final String CLIENT_ID = "android-login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnData = findViewById(R.id.dataBtn);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }

    public void getData() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DataService booksTestService = new Retrofit.Builder()
                .baseUrl("http://192.168.0.85:8050")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DataService.class);

        String resp = LoginActivity.getAccessTokenString();

        Call<String> call = booksTestService.getDataTest("Bearer " + resp);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    Toast.makeText(MenuActivity.this, responseBody, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error: " + t, Toast.LENGTH_LONG).show();
            }
        });

    }

}
