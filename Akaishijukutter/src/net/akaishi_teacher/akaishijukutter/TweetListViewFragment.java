package net.akaishi_teacher.akaishijukutter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TweetListViewFragment extends ListFragment
{
	private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";

	SharedPreferences p;
	Twitter mTwitter;
	private ListView _listView = null;
	private int _listPosition = 0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		View view = inflater.inflate(R.layout.list_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		p = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String str = p.getString("pref_get_num", "20");
		Paging paging = new Paging(1, Integer.parseInt(str));
		
		TweetAdapter mAdapter;
		if(_listView != null){
			mAdapter  = (TweetAdapter) _listView.getAdapter();
			}
		else{
			mAdapter = new TweetAdapter(getActivity());
			setListAdapter(mAdapter);
			mTwitter = TwitterUtils.getTwitterInstance(getActivity());
			reloadTimeLine(getArguments().getInt("mode"), paging);
			
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// インスタンス保存状態から表示位置取得
		if(savedInstanceState != null) {
			_listPosition = savedInstanceState.getInt(KEY_LIST_POSITION);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();

		_listView = getListView();
		if(_listView == null)
			return;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if(_listView != null) {

			// REMARK
			// ここでgetListViewすると意味不明な例外が発生してしまうため、
			// onActivityCreated内で取得していたListViewオブジェクトを
			// 使って表示位置を取得している
			_listPosition = _listView.getFirstVisiblePosition();

		}

		// Fragmentの表示状態を保存する
		// この状態を復帰するのはonCreate内である
		outState.putInt(KEY_LIST_POSITION, _listPosition);
	}




	public void reloadTimeLine(final int mode, final Paging paging)
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
			protected void onPostExecute(List<twitter4j.Status> result) {
				TweetAdapter mAdapter = (TweetAdapter) _listView.getAdapter();
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
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
	private void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}
}
