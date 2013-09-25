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
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class DMListViewFragment extends BaseListFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		reloadTimeLine(false);
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
	
	public void reloadTimeLine(boolean flag)
	{
		p = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String str = p.getString("pref_get_num", "20");
		Paging paging = new Paging(1, Integer.parseInt(str));

		DMAdapter mAdapter;
		if(_listView != null){
			mAdapter  = ((DMAdapter) ((HeaderViewListAdapter)_listView.getAdapter()).getWrappedAdapter());
			if(flag)
				getData(getArguments().getInt("mode"), paging);
		}
		else{
			mAdapter = new DMAdapter(getActivity());
			setListAdapter(mAdapter);
			mTwitter = TwitterUtils.getTwitterInstance(getActivity());
			getData(getArguments().getInt("mode"), paging);
		}
	}

	public void getData(final int mode, final Paging paging)
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
				DMAdapter mAdapter = ((DMAdapter) ((HeaderViewListAdapter)_listView.getAdapter()).getWrappedAdapter());
				
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.DirectMessage status : result) {
						mAdapter.add(status);
					}
					_listView.setSelection(0);
				} else {
					showToast("タイムラインの取得に失敗しました。。。");
				}
				if(mPullToRefreshListView != null)
					mPullToRefreshListView.onRefreshComplete();
			}
		};
		task.execute();
	}
	
	@Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
    {
		reloadTimeLine(true);
    }

	@Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
    {
    }
}
