package com.example.emantest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emanteast.db.MovieDatabaseHelper;

public class DetailFragment extends Fragment {

	private long id = -1;

	public DetailFragment() {
		super();
	}

	public DetailFragment(long id) {
		super();
		this.id = id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail_movie, container, false);
		if (id >= 0) {
			updateDetail(view);
		}
		return view;
	}

	public void updateDetail(View view) {
		MovieDatabaseHelper db = new MovieDatabaseHelper(getActivity());
		MovieEntity movie = db.populateMovie(db.getObject(id));
		if (movie != null) {
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView description = (TextView) view.findViewById(R.id.description);
			TextView cast = (TextView) view.findViewById(R.id.cast);
			ImageView poster = (ImageView) view.findViewById(R.id.image_poster);
			
			TextView description_label = (TextView) view.findViewById(R.id.description_label);
			TextView cast_label = (TextView) view.findViewById(R.id.cast_label);

			title.setText(movie.getTitle());
			description.setText(movie.getSynopsis());
			cast.setText(movie.getAbridged_cast());
			poster.setImageURI(Uri.parse(movie.getPoster()));
			
			description_label.setText(R.string.description);
			cast_label.setText(R.string.cast);
			
			
		} else {
			TextView empty = (TextView) view.findViewById(R.id.empty_detail);
			empty.setText(R.string.empty_detail);
		}
	}

}
