package net.akaishi_teacher.akaishijukutter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends Activity
{
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_home);
		
		
		
		if (!TwitterUtils.hasAccessToken(this)) {
			Intent intent = new Intent(this, TwitterAuthActivity.class);
			startActivity(intent);
			finish();
		}else {
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			PagerAdapter mPagerAdapter = new TopViewPagerAdapter(this);
			viewPager.setAdapter(mPagerAdapter);
			
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
	        	
	            //reloadTimeLine();
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
