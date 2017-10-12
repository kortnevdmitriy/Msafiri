package ai.kortnevdmitriy.msafiri.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jetradar.desertplaceholder.DesertPlaceholder;

import ai.kortnevdmitriy.msafiri.R;

public class DealOffers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_offers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DesertPlaceholder desertPlaceholder = findViewById(R.id.placeholder);
        desertPlaceholder.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
            }
        });
    }

}
