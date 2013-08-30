package net.akaishi_teacher.akaishijukutter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends ListActivity
{
	private TweetAdapter mAdapter;
	private Twitter mTwitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!TwitterUtils.hasAccessToken(this)) {
			Intent intent = new Intent(this, TwitterAuthActivity.class);
			startActivity(intent);
			finish();
		}else {
			mAdapter = new TweetAdapter(this);
			setListAdapter(mAdapter);
			
			ListView lv = getListView();
			ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.drawable.separate_line));
			lv.setDivider(sage);
			lv.setDividerHeight(2);
			
			mTwitter = TwitterUtils.getTwitterInstance(this);
			reloadTimeLine();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	private void reloadTimeLine() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					Paging paging = new Paging(1, 10);
					return mTwitter.getHomeTimeline(paging);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					int i = 0;
					boolean flag = false;
					if(mAdapter.isEmpty())
						flag = true;
					
					for (twitter4j.Status status : result) {
						if(flag)
						{
							mAdapter.add(status);
						}else
						{
							mAdapter.insert(status, i);
							i++;
						}
					}
					getListView().setSelection(0);
				} else {
					showToast("タイムラインの取得に失敗しました。。。");
				}
			}
		};
		task.execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_refresh:
	            reloadTimeLine();
	            return true;
	        case R.id.menu_tweet:
	        	Intent tweet = new Intent(this, TweetActivity.class);
	        	startActivity(tweet);
	        	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

}
