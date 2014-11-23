package com.example.emantest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends ArrayAdapter<MovieEntity> {

	private int resource;
	private List<Long> ids = new ArrayList<Long>();
	private Context context;

	public MovieAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.resource = resource;
	}

	public void addAll(Collection<? extends MovieEntity> collection) {
		super.addAll(collection);
	}

	private ViewHolder viewHolder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final View view = getWorkingView(convertView);
		viewHolder = getViewHolder(view);
		final MovieEntity entry = getItem(position);
		viewHolder.titleView.setText(entry.getTitle());
		// viewHolder.imageView.setImageBitmap();
		if (isNetworkAvailable()) {
			new ImageDownloadTask(viewHolder.imageView).execute(entry.getPoster());
		}
		return view;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

	class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap> {
		private ImageView mView;

		ImageDownloadTask(ImageView view) {
			mView = view;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(new URL(params[0]).openConnection().getInputStream());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			mView.setImageBitmap(result);
		}
	}
}
