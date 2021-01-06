package com.example.musicaldelight;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignOut extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 999;
    List<AuthUI.IdpConfig> providers;
//    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_out);

//        btn=findViewById(R.id.btn_sign_out);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        btn.setEnabled(false);
//                        showSignInOptions();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        providers= Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),   //Email builder
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


        showSignInOptions();

    }

    @Override
    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
        finishAffinity();
        finish();

    }

    public void showSignInOptions() {
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),
                MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE)
        {
            IdpResponse idpResponse=IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                //get user
//                TextView text=findViewById(R.id.textView);
//                NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
//
//                TextView userName = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.textView);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //show email on toast

                assert user != null;
                Toast.makeText(this,""+user.getEmail(),Toast.LENGTH_SHORT).show();
//                for (UserInfo profile : user.getProviderData()) {
//                    // Id of the provider (ex: google.com)
//                    String providerId = profile.getProviderId();
//
//                    // UID specific to the provider
//                    String uid = profile.getUid();
//
//                    // Name, email address, and profile photo Url
//                    String name = profile.getDisplayName();
//                    Uri photoUrl = profile.getPhotoUrl();
//                    userName.setText(name);
//
//                }
//                Dexter.withActivity(this)
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override public void onPermissionGranted(PermissionGrantedResponse response)
//                            {
//                                Intent intent=new Intent(SignOut.this,MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                            @Override public void onPermissionDenied(PermissionDeniedResponse response)
//                            {
//
//                            }
//
//                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
//                            {
//                                token.continuePermissionRequest();
//                            }
//                        }).check();

                Dexter.withActivity(this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                                )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // do you work now
                                    Intent intent=new Intent(SignOut.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // permission is denied permenantly, navigate user to app settings
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .onSameThread()
                        .check();


            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                assert idpResponse != null;
//                Toast.makeText(this,""+ Objects.requireNonNull(idpResponse.getError()).getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
