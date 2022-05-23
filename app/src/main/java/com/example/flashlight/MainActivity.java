package com.example.flashlight;


import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


// most important term here:
// appCompat changed to androidx as in version 6.2.3 it isn't working in my device and also
// their is the occurrence of error in getCameraId()[0];

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton; // image will work as a button
    private boolean state; // to know the state of flash (ON/OFF)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.torchBtn);

//    used 'Dexter' for Camera's run time permission

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashLight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera Permission is required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void runFlashLight() {

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state == false) {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];

//                      state = false and imageButton have been clicked
//                      then state should change to true

                        state = true;
                        cameraManager.setTorchMode(cameraId, state);
                        imageButton.setImageResource(R.drawable.torch_on); // change the image
                    } catch (CameraAccessException e) {
                    }
                } else {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];

//                      state = false and imageButton have been clicked
//                      then state should change to true
                        state = false;
                        cameraManager.setTorchMode(cameraId, state);
                        imageButton.setImageResource(R.drawable.torch_off);
                    } catch (CameraAccessException e) {
                    }
                }
            }
        });

    }
}