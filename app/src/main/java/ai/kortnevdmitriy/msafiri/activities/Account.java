package ai.kortnevdmitriy.msafiri.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ai.kortnevdmitriy.msafiri.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String displayName = user.getDisplayName();
        String userEmail = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        Uri photoUrl = user.getPhotoUrl();


        TextView tvDisplayName = findViewById(R.id.user_profile_name);
        TextView tvDisplayBio = findViewById(R.id.user_profile_short_bio);
        TextView tvDisplayEmail = findViewById(R.id.user_profile_email);
        TextView tvDisplayPhone = findViewById(R.id.user_profile_phone);
        CircleImageView avatarImageView = findViewById(R.id.user_profile_photo);

        Glide.with(getApplicationContext())
                .load(photoUrl)
                .into(avatarImageView);
        tvDisplayName.setText(displayName);
        tvDisplayEmail.setText(userEmail);
        tvDisplayPhone.setText("0706025924");
    }

}
