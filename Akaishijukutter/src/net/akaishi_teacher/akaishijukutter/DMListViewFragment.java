package net.akaishi_teacher.akaishijukutter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DMListViewFragment extends BaseListFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_view, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		p = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String str = p.getString("pref_get_num", "20");
		Paging paging = new Paging(1, Integer.parseInt(str));

		DMAdapter mAdapter;
		if(_listView != null){
			mAdapter  = (DMAdapter) _listView.getAdapter();
		}
		else{
			mAdapter = new DMAdapter(getActivity());
			setListAdapter(mAdapter);
			mTwitter = TwitterUtils.getTwitterInstance(getActivity());
			reloadTimeLine(getArguments().getInt("mode"), paging);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void reloadTimeLine(final int mode, final Paging paging)
	{
		AsyncTask<Void, Void, List<twitter4j.DirectMessage>> task = new AsyncTask<Void, Void, List<twitter4j.DirectMessage>>() {
			@Override
			protected List<twitter4j.DirectMessage> doInBackground(Void... params) {
				try {
					switch(mode)
					{
					case 0:
						return mTwitter.getDirectMessages(paging);
					}

				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.DirectMessage> result)
			{
				DMAdapter mAdapter = (DMAdapter) _listView.getAdapter();
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.DirectMessage status : result) {
						mAdapter.add(status);
					}
					_listView.setSelection(0);
				} else {
					showToast("タイムラインの取得に失敗しました。。。");
				}
			}
		};
		task.execute();
	}
}
