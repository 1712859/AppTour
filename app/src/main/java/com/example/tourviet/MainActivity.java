package com.example.tourviet;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if(editUserdk.getText().length()!=0 &&editPassdk.getText().length()!=0 && editEmaildk.getText().length()!=0 && editSDTdk.getText().length()!=0  && editHodk.getText().length()!=0 && editTendk.getText().length()!=0) {
                    editUser.setText(User);
                    editPass.setText(Pass);
                    dialog.cancel();
                }
                else
                    Toast.makeText(MainActivity.this,"mời các bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();

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
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().length()!=0 && editPass.getText().length()!=0)
                {
                    if(editUser.getText().toString().equals(User) && editPass.getText().toString().equals(Pass))
                    {
                        Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);

                    }else
                        if(editUser.getText().toString().equals("admin") && editPass.getText().toString().equals("admin"))
                        {
                            Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                            startActivity(intent);
                        }else
                            Toast.makeText(MainActivity.this,"Bạn đã đăng nhập thất bại",Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(MainActivity.this,"mời các bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();

                }
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

