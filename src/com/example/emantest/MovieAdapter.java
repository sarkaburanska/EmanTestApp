package com.example.emantest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends ArrayAdapter<MovieEntity> {

	private int resource;
	private List<Long> ids = new ArrayList<Long>();

	public MovieAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public void addAll(Collection<? extends MovieEntity> collection) {
		for (MovieEntity m : collection) {
			long id = Long.valueOf(m.getId());
			if (ids.contains(id)) {
				continue;
			}
			ids.add(id);
			super.add(m);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final View view = getWorkingView(convertView);
		final ViewHolder viewHolder = getViewHolder(view);
		final MovieEntity entry = getItem(position);
		viewHolder.titleView.setText(entry.getTitle());
		return view;
	}

	private View getWorkingView(final View convertView) {
		View workingView = null;

		if (null == convertView) {
			final Context context = getContext();
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			workingView = inflater.inflate(resource, null);
		} else {
			workingView = convertView;
		}

		return workingView;
	}

	private ViewHolder getViewHolder(final View workingView) {
		final Object tag = workingView.getTag();
		ViewHolder viewHolder = null;

		if (null == tag || !(tag instanceof ViewHolder)) {
			viewHolder = new ViewHolder();

			viewHolder.imageView = (ImageView) workingView.findViewById(R.id.movie_image);
			viewHolder.titleView = (TextView) workingView.findViewById(R.id.movie_title);

			workingView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) tag;
		}

		return viewHolder;
	}

	private static class ViewHolder {
		public TextView titleView;
		public ImageView imageView;
	}

}
