package net.akaishi_teacher.akaishijukutter;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
	private ArrayList<PageItem> mList;

	public MyFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
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
			bundle.putInt("mode", position);
			listFragment.setArguments(bundle);

			return listFragment;
		}
		else
		{
			TweetListViewFragment listFragment = new TweetListViewFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mode", position);
			listFragment.setArguments(bundle);

			return listFragment;
		}
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

	public void addItem(PageItem item) {
		mList.add(item);
	}


	public int getItemPosition(Object object) {

		return POSITION_NONE;

	}

}
