package com.example.cuoiki_android_lythuyet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cuoiki_android_lythuyet.data.MemoryData;
import com.example.cuoiki_android_lythuyet.databinding.ActivitySignupBinding;
import com.example.cuoiki_android_lythuyet.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://survivorio-28711-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignin.setOnClickListener(v -> {
            Intent i = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(i);
        });

        binding.buttonSignup.setOnClickListener(v -> registerUser(binding.editTextEmail.getText().toString(), binding.etPassword.getText().toString()));
    }

    private void registerUser(String textEmail, String textPassword) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Log.d("TAG", "createUserWithEmail:success");
                            Log.d("TAG", "uid: " + user.getUid());
                            Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(binding.editTextUsername.getText().toString())
                                    .setPhotoUri(Uri.parse("https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2022/05/hinh-avatar-doi-dep-2022-6-696x696.jpg?fit=700%2C20000&quality=95&ssl=1"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d("TAG", "User profile updated.");
                                        }
                                    });

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    databaseReference.child("Users").child(user.getUid()).child("uid").setValue(user.getUid());
                                    Log.d("TAG", "onDataChange: " + snapshot.getValue());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d("TAG", "onCancelled: " + databaseError.getMessage());
                                }
                            });

                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
        //add name to user

    }
}