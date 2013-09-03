package net.akaishi_teacher.akaishijukutter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TopViewPagerAdapter extends PagerAdapter
{
	public final static int N = 2;
	private LayoutInflater inflater = null;
	private Context c;
	private Twitter mTwitter;

	public TopViewPagerAdapter(Context c) {
		super();
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.c = c;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		ListView lv = (ListView) inflater.inflate(R.layout.list_view, null);
		final TweetAdapter mAdapter = new TweetAdapter(c);
		TextView tv = new TextView(c);
		int brt = 255*position/N;
		lv.setBackgroundColor(Color.rgb(brt,brt,brt));//適当に色をセット(しなくていい)
		tv.setTextColor(c.getResources().getColor(R.color.white));
		
		switch(position)
		{
		case 0:
			tv.setText("HOME");
			break;
		case 1:
			tv.setText("MENTION");
			break;
		}
		lv.setAdapter(mAdapter);
		
		
		ColorDrawable sage = new ColorDrawable(c.getResources().getColor(R.drawable.separate_line));
		lv.setDivider(sage);
		lv.setDividerHeight(2);
		
		mTwitter = TwitterUtils.getTwitterInstance(c);
		
		container.addView(lv);
		reloadTimeLine(container, position);
		
		return lv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getCount() {
		return N;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}
	
	@Override
    public int getItemPosition(Object object)
    {
	    return super.getItemPosition(object);
    }

	@Override
    public CharSequence getPageTitle(int position)
    {
	    return super.getPageTitle(position);
    }

	public void reloadTimeLine(final ViewGroup container, final int position) {
		AsyncTask<Integer, Void, List<twitter4j.Status>> task = new AsyncTask<Integer, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Integer... arg0)
			{
				try {
					Paging paging = new Paging(1, 10);
					switch(arg0[0])
					{
					case 0:
						return mTwitter.getHomeTimeline(paging);
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
					int i = 0;
					boolean flag = false;
					TweetAdapter currentAdapter = ((TweetAdapter) ((ListView) container.getChildAt(position)).getAdapter());
					
					if(currentAdapter.isEmpty())
						flag = true;
					
					for (twitter4j.Status status : result)
					{
						if(flag)
						{
							currentAdapter.add(status);
						}else
						{
							currentAdapter.insert(status, i);
							i++;
						}
					}
					//lv.setSelection(0);
				} else {
					showToast("タイムラインの取得に失敗しました。。。");
				}
			}
		};
		task.execute(position);
	}
	private void showToast(String text) {
		Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
	}
}