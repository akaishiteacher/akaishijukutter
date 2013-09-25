package net.akaishi_teacher.akaishijukutter;

import twitter4j.Paging;
import twitter4j.Twitter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class BaseListFragment extends ListFragment implements OnRefreshListener2<ListView>
{
	private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";

	SharedPreferences p;
	Twitter mTwitter;
	ListView _listView = null;
	protected PullToRefreshListView mPullToRefreshListView;
	private int _listPosition = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		View view = inflater.inflate(R.layout.list_view, container, false);
		
		
		
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("ガンガン引っ張っちゃって！");
		mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("らめぇ");
		mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("取得中ですよ");
		mPullToRefreshListView.setOnRefreshListener(this);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		if(_listView != null)
		{
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

	public void getData(final int mode, final Paging paging)
	{

	}

	void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	@Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
    {
    }

	@Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
    {
    }

}
