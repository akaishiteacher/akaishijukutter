package net.akaishi_teacher.akaishijukutter;

import twitter4j.Paging;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends FragmentActivity
{
	ViewPager pager;
	SharedPreferences p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_home);

		if (!TwitterUtils.hasAccessToken(this))
		{
			Intent intent = new Intent(this, TwitterAuthActivity.class);
			startActivity(intent);
			finish();
		}else
		{
			p = PreferenceManager.getDefaultSharedPreferences(this);
			
			pager = (ViewPager) findViewById(R.id.viewpager);

			// PagerTitleStrip のカスタマイズ
			PagerTabStrip strip = (PagerTabStrip) findViewById(R.id.strip);
			strip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			strip.setTextColor(Color.WHITE);
			strip.setTextSpacing(50);
			strip.setNonPrimaryAlpha(0.3f);
			strip.setDrawFullUnderline(true);
			strip.setTabIndicatorColor(0xcc3333);

			// ViewPager の Adapter
			MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

			PageItem home = new PageItem();
			home.title = "home";
			home.fragmentKind = PageItem.TWEET;
			home.TweetKind = PageItem.HOME;
			adapter.addItem(home);

			PageItem reply = new PageItem();
			reply.title = "reply";
			reply.fragmentKind = PageItem.TWEET;
			reply.TweetKind = PageItem.REPLY;
			adapter.addItem(reply);

			PageItem dm = new PageItem();
			dm.title = "DM";
			dm.fragmentKind = PageItem.DM;
			dm.TweetKind = PageItem.DMBODY;
			adapter.addItem(dm);

			pager.setAdapter(adapter);

			pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() 
			{
				@Override

				public void onPageSelected(int position) 
				{
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		MyFragmentPagerAdapter adapter = (MyFragmentPagerAdapter) pager.getAdapter();
		int position = pager.getCurrentItem();

		switch (item.getItemId()) 
		{
		case R.id.menu_refresh:
			String str = p.getString("pref_get_num", "20");
			Paging paging = new Paging(1, Integer.parseInt(str));
			
			if(adapter.getFragmentKind(position) == PageItem.TWEET)
			{
				TweetListViewFragment  listFragment = (TweetListViewFragment) adapter.getFragment(position);
				listFragment.reloadTimeLine(adapter.getTweetKind(position), paging);
			}
			else if(adapter.getFragmentKind(position) == PageItem.DM)
			{
				DMListViewFragment listFragment = (DMListViewFragment) adapter.getFragment(position);
				listFragment.reloadTimeLine(adapter.getTweetKind(position), paging);
			}
			return true;
		case R.id.menu_tweet:
			Intent tweet = new Intent(this, TweetActivity.class);
			startActivity(tweet);
			return true;
		case R.id.action_settings:
		      startActivity(new Intent(this, MyPreferences.class));
		}
		return super.onOptionsItemSelected(item);
	}

	//	private void showToast(String text) {
	//		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	//	}

}
