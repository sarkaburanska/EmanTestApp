package com.example.emantest;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class MyScrollListener implements OnScrollListener {

	ListMovieFragment list;
	private static int SCROLL_OFFSET = 5;
	private int visibleThreshold = 2;
	private int currentPage = 0;
	private int previousTotal = 0;
	private boolean loading = true;
	private boolean userScrolled = false;

	public MyScrollListener(ListMovieFragment list) {
		super();
		this.list = list;
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
            userScrolled = true;
        } 
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (loading) {
			if (totalItemCount > previousTotal) {
				loading = false;
				previousTotal = totalItemCount;
				currentPage++;
			}
			return;
		}
		if (!loading && userScrolled &&(totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			list.addNextPageToList();
			loading = true;
		}
	}
}
