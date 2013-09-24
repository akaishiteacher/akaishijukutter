package net.akaishi_teacher.akaishijukutter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
	private ArrayList<PageItem> mList;
	private Map<Integer, String> mFragmentTags;
	private FragmentManager mFragmentManager;

	public MyFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
		mFragmentManager = fm;
		mFragmentTags = new HashMap<Integer, String>();
		mList = new ArrayList<PageItem>();
	}

	@Override
	public Fragment getItem(int position)
	{
		PageItem item = mList.get(position);
		if(PageItem.DM == item.fragmentKind)
		{
			DMListViewFragment listFragment = new DMListViewFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mode", item.TweetKind);
			listFragment.setArguments(bundle);

			return listFragment;
		}
		else
		{
			TweetListViewFragment listFragment = new TweetListViewFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mode", item.TweetKind);
			listFragment.setArguments(bundle);

			return listFragment;
		}
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) 
	{
		Object obj = super.instantiateItem(container, position);
		if (obj instanceof Fragment) {
			// record the fragment tag here.
			Fragment f = (Fragment) obj;
			String tag = f.getTag();
			mFragmentTags.put(position, tag);
		}
		return obj;
	}
	public Fragment getFragment(int position)
	{
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }
	
	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return mList.get(position).title;
	}
	
	public int getFragmentKind(int position)
	{
		return mList.get(position).fragmentKind;
	}
	public int getTweetKind(int position)
	{
		return mList.get(position).TweetKind;
	}

	public void addItem(String title,int fragmentKind, int tweetkind)
	{
		PageItem item = new PageItem();
		item.title = title;
		item.fragmentKind = fragmentKind;
		item.TweetKind = tweetkind;
		mList.add(item);
	}


	public int getItemPosition(Object object) {

		return POSITION_NONE;

	}

}
