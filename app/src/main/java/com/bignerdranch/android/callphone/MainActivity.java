package com.bignerdranch.android.callphone;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);

                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기 \"권한이 필요" +
                                            "합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {


                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                                        1000);
                                            }
                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                    1000);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-9027-6455"));
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-9027-6455"));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(MainActivity.this, "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
