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

public class TweetListViewFragment extends BaseListFragment
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

		TweetAdapter mAdapter;
		if(_listView != null){
			mAdapter  = (TweetAdapter) ((TweetAdapter) ((HeaderViewListAdapter)_listView.getAdapter()).getWrappedAdapter());
			if(flag)
				getData(getArguments().getInt("mode"), paging);
		}
		else{
			mAdapter = new TweetAdapter(getActivity());
			setListAdapter(mAdapter);
			mTwitter = TwitterUtils.getTwitterInstance(getActivity());
			getData(getArguments().getInt("mode"), paging);
		}
	}

	public void getData(final int mode, final Paging paging)
	{

		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					switch(mode)
					{
					case 0:
						return mTwitter.getHomeTimeline(paging);
					case 1:
						return mTwitter.getMentionsTimeline(paging);
					}

				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result)
			{
				TweetAdapter mAdapter = ((TweetAdapter) ((HeaderViewListAdapter)_listView.getAdapter()).getWrappedAdapter());
				
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
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
