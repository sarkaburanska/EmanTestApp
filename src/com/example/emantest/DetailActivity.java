package com.example.emantest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class DetailActivity extends FragmentActivity {

	public static final String EXTRA_ID = "id";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_fragment);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);

        Fragment f = new DetailFragment(id);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail, f);
        ft.commit();
    }

	
}
