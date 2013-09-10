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

public class DMListViewFragment extends ListFragment
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

		DMAdapter mAdapter = new DMAdapter(getActivity());
		mTwitter = TwitterUtils.getTwitterInstance(getActivity());

		reloadTimeLine(getArguments().getInt("mode"), mAdapter);

		setListAdapter(mAdapter);

	}



	public void reloadTimeLine(final int mode, final DMAdapter mAdapter)
	{

		AsyncTask<Void, Void, List<twitter4j.DirectMessage>> task = new AsyncTask<Void, Void, List<twitter4j.DirectMessage>>() {
			@Override
			protected List<twitter4j.DirectMessage> doInBackground(Void... params) {
				try {
					switch(mode)
					{
					case 2:
						return mTwitter.getDirectMessages();
					}
					
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.DirectMessage> result) {
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.DirectMessage status : result) {
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
