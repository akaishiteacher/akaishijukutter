package net.akaishi_teacher.akaishijukutter;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TweetListViewFragment extends ListFragment
{
	Twitter mTwitter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_view, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TweetAdapter mAdapter = new TweetAdapter(getActivity());
		mTwitter = TwitterUtils.getTwitterInstance(getActivity());

		reloadTimeLine(getArguments().getInt("mode"), mAdapter);

		setListAdapter(mAdapter);

	}



	public void reloadTimeLine(final int mode, final TweetAdapter mAdapter)
	{

		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					switch(mode)
					{
					case 0:
						return mTwitter.getHomeTimeline();
					case 1:
						return mTwitter.getMentionsTimeline();
					}
					
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
					getListView().setSelection(0);
				} else {
					showToast("タイムラインの取得に失敗しました。。。");
				}
			}
		};
		task.execute();
	}
	private void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}
}
