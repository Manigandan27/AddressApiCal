package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPincode;
    private Button buttonGetAreaName;
    private TextView tv_areaname;
    private ProgressDialog progressDialog;
    private Call<List<PostOfficeResponse>> areaNameCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPincode = findViewById(R.id.editTextPincode);
        buttonGetAreaName = findViewById(R.id.buttonGetAreaName);
        tv_areaname = findViewById(R.id.tv_areaname);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Area Name...");
        progressDialog.setCancelable(false);

        buttonGetAreaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pincode = editTextPincode.getText().toString();
                getAreaName(pincode);
            }
        });
    }

    private void getAreaName(String pincode) {
        if (areaNameCall != null) {
            areaNameCall.cancel();
        }

        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        areaNameCall = apiService.getPincodeData(pincode);

        progressDialog.show();

        areaNameCall.enqueue(new Callback<List<PostOfficeResponse>>() {
            @Override

            public void onResponse(Call<List<PostOfficeResponse>> call, Response<List<PostOfficeResponse>> response) {
                progressDialog.dismiss();
                if (!call.isCanceled() && response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    List<PostOfficeResponse> resultList = response.body();
                    // Get the first PostOfficeResponse
                    PostOfficeResponse postOfficeResponse = resultList.get(0);

                    // Check if the PostOffice list is not null and not empty
                    if (postOfficeResponse.getPostOfficeList() != null && postOfficeResponse.getPostOfficeList().size() > 0) {
                        // Get the first PostOffice from the list
                        PostOfficeResponse.PostOffice firstPostOffice = postOfficeResponse.getPostOfficeList().get(0);

                        // Display the name of the first post office
                        tv_areaname.setText(firstPostOffice.getName());
                    } else {
                        showToast("No post offices found for the given pincode.");
                    }
                } else {
                    showToast("Failed to fetch area name. Please check the pin code.");
                }
            }


            @Override
            public void onFailure(Call<List<PostOfficeResponse>> call, Throwable t) {
                progressDialog.dismiss();
                if (!call.isCanceled()) {
                    // Use runOnUiThread to update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_areaname.setText("Error: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
