package net.akaishi_teacher.akaishijukutter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends FragmentActivity
{
	ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_home);
		
		
		
		if (!TwitterUtils.hasAccessToken(this)) {
			Intent intent = new Intent(this, TwitterAuthActivity.class);
			startActivity(intent);
			finish();
		}else {
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

	        // 各ページアイテム(おすすめアプリ)
	        PageItem home = new PageItem();
	        home.title = "home";
	        home.fragmentKind = PageItem.TWEET;
	        adapter.addItem(home);
	        
	        PageItem reply = new PageItem();
	        reply.title = "replay";
	        reply.fragmentKind = PageItem.TWEET;
	        adapter.addItem(reply);
	        
	        PageItem dm = new PageItem();
	        dm.title = "DM";
	        dm.fragmentKind = PageItem.DM;
	        adapter.addItem(dm);
	        
	        pager.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_refresh:
	        	
	        	pager.getAdapter().notifyDataSetChanged();
	            return true;
	        case R.id.menu_tweet:
	        	Intent tweet = new Intent(this, TweetActivity.class);
	        	startActivity(tweet);
	        	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
//	private void showToast(String text) {
//		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//	}

}
