package ai.kortnevdmitriy.msafiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ai.kortnevdmitriy.msafiri.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavigationViewUI();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_direct_book) {
            startActivity(new Intent(getApplicationContext(), DirectBook.class));
            return true;
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        }
        if (id == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Signin.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_all) {
            startActivity(new Intent(getApplicationContext(), ViewAll.class));
        } else if (id == R.id.nav_my_trips) {
            startActivity(new Intent(getApplicationContext(), MyTrips.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(getApplicationContext(), Help.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(getApplicationContext(), Account.class));
        } else if (id == R.id.nav_deals) {
            startActivity(new Intent(getApplicationContext(), DealOffers.class));
        } else if (id == R.id.nav_my_tickets) {
            startActivity(new Intent(getApplicationContext(), Tickets.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* This methods update the navigation views with the account owner details.
   Picture, name & email*/
    public void updateNavigationViewUI() {
        View header = navigationView.getHeaderView(0);
        CircleImageView picView = header.findViewById(R.id.picView);
        TextView nameView = header.findViewById(R.id.nameView);
        TextView emailView = header.findViewById(R.id.emailView);

        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        nameView.setText(mFirebaseUser.getDisplayName());
        emailView.setText(mFirebaseUser.getEmail());
        Glide.with(this).load(mFirebaseUser.getPhotoUrl()).into(picView);
    }
}
