package com.example.tourviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InviteToTour extends AppCompatActivity {
    String id;
    String token;
    EditText email;
    Spinner spinner;
    Button button;
    Long id_;
    List<gest_user> userData = new ArrayList<>();
    gest_user User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_tour);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id = intent.getStringExtra("Key_2");
            token = intent.getStringExtra("Key_1");
        }
        Anhxa();

        String[] items = new String[]{"True","False"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InviteToTour.this, R.layout.spinner_item,items);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Userlistget userlistget = new Userlistget(
                        email.getText().toString()
                );
                Retrofit builder= new Retrofit.Builder()
                        .baseUrl("http://35.197.153.192:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                userClient client =builder.create((userClient.class));
                final Call<Userlistget> call= client.searchUser(userlistget.searchKey,1,10);
                call.enqueue(new Callback<Userlistget>() {
                    @Override
                    public void onResponse(Call<Userlistget> call, Response<Userlistget> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(InviteToTour.this, "Email hay SDT không đúng!!! xin nhâp lại", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            Userlistget user = response.body();
                            userData.clear();
                            final boolean b = userData.addAll(user.getUsers());
                            if(b==false)
                            {
                                Toast.makeText(InviteToTour.this, "Email hay SDT không đúng!!! xin nhâp lại", Toast.LENGTH_LONG).show();
                                return;
                            };
                            userData.clear();
                            userData.addAll(user.getUsers());
                            User = userData.get(0);
                            id_ = User.getId();
                            if(id_==null)
                                Toast.makeText(InviteToTour.this, "Email hay SDT không đúng!!! xin nhâp lại", Toast.LENGTH_LONG).show();
                                return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Userlistget> call, Throwable t) {
                        Toast.makeText(InviteToTour.this,"email hoặc sđt sai",Toast.LENGTH_SHORT).show();
                    }
                });
                Boolean term = false;
                if(spinner.getSelectedItem().toString()=="True")
                    term = true;
                add_member_class addMemberClass = new add_member_class(
                        String.valueOf(id),
                        String.valueOf(id_),
                        term
                );
                Call<add_member_class> call_ = client.addmember(token,addMemberClass);
                call_.enqueue(new Callback<add_member_class>() {
                    @Override
                    public void onResponse(Call<add_member_class> call, Response<add_member_class> response) {

                        if(!response.isSuccessful())
                        {
                            Toast.makeText(InviteToTour.this, "Người này đã có trong tour", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(InviteToTour.this, "Mời thành công", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<add_member_class> call, Throwable t) {
                        Toast.makeText(InviteToTour.this, "lỗiiiiii", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }

    private void Anhxa() {
        email = (EditText)findViewById(R.id.Add_email);
        button = (Button)findViewById(R.id.Add_button);
        spinner = (Spinner)findViewById(R.id.Add_spiner);
    }
}
