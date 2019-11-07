package com.example.tourviet;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editUser,editPass;
    Button buttonDangKi,buttonDangNhap,buttonThoat;
    String User,Pass,Email,SDT,Ho,Ten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        controlButton();
    }

    private void controlButton() {
        buttonThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có chắc muốn thoát khỏi app");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        buttonDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dangki);
                final EditText editUserdk = (EditText)dialog.findViewById(R.id.dangki_user);
                final EditText editPassdk = (EditText)dialog.findViewById(R.id.dangki_Pass);
                final EditText editEmaildk = (EditText)dialog.findViewById(R.id.dangki_email);
                final EditText editHodk = (EditText)dialog.findViewById(R.id.dangki_Ho);
                final EditText editSDTdk = (EditText)dialog.findViewById(R.id.dangki_SDT);
                final EditText editTendk = (EditText)dialog.findViewById(R.id.dangki_Ten);
                Button buttonDangKi = (Button)dialog.findViewById(R.id.buttonDangKi_dangki);
                Button buttonHuy = (Button)dialog.findViewById(R.id.buttonDangKi_huy);
                buttonDangKi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User =  editUserdk.getText().toString().trim();
                        Pass =  editPassdk.getText().toString().trim();
                        Email =  editEmaildk.getText().toString().trim();
                        SDT =  editSDTdk.getText().toString().trim();
                        Ho =  editHodk.getText().toString().trim();
                        Ten =  editTendk.getText().toString().trim();

                        editUser.setText(User);
                        editPass.setText(Pass);
                        dialog.cancel();
                    }
                });
                buttonHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                    }
                });
                dialog.show();
            }
        });
    }

    private void Anhxa() {
        editUser = (EditText)findViewById(R.id.edittextuser);
        editPass = (EditText)findViewById(R.id.edittextpass);
        buttonDangKi = (Button)findViewById((R.id.buttonDangKi));
        buttonDangNhap = (Button)findViewById(R.id.buttonDangNhap);
        buttonThoat = (Button)findViewById((R.id.buttonThoat));


    }

}

