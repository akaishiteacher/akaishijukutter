package net.akaishi_teacher.akaishijukutter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class MyPreference extends PreferenceActivity implements OnPreferenceChangeListener
{
	private EditTextPreference reload_limit;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		
		reload_limit = (EditTextPreference)findPreference("reload_limit");
		
		reload_limit.setOnPreferenceChangeListener(this);
		
	}

	@Override
	public boolean onPreferenceChange(Preference arg0, Object arg1)
	{
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
