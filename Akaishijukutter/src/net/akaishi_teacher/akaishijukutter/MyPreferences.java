package net.akaishi_teacher.akaishijukutter;

import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;

public class MyPreferences extends PreferenceActivity implements OnPreferenceChangeListener
{
	final static String TAG = "Preference";

	private EditTextPreference get_num;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);
		addPreferencesFromResource(R.xml.pref);
		// Preferenceの取得
		get_num = (EditTextPreference)findPreference("pref_get_num");
		
		// リスナーを設定する
		get_num.setOnPreferenceChangeListener(this);

		// 保存されたデータを読み込む
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

		// 値の取得
		String param_get_num = p.getString("pref_get_num", "20");

		// デフォルト値の設定
		get_num.setDefaultValue(param_get_num);
		
		// サマリーの設定
		setSummary(get_num, param_get_num);

		Button button = (Button) findViewById(R.id.button);

		// ボタン押下時の処理
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					Intent intent = new Intent(MyPreferences.this, HomeActivity.class);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"ERROR\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public boolean onPreferenceChange(Preference preference, Object newValue)
	{
		if(newValue != null){
			// newValueの型でサマリーの設定を分ける
			if(newValue instanceof String)
			{
				// preferenceの型でサマリーの設定を分ける
				if(preference instanceof ListPreference)
					setSummary((ListPreference)preference, (String)newValue);
				else if(preference instanceof EditTextPreference)
					setSummary((EditTextPreference)preference, (String)newValue);
				else if(preference instanceof RingtonePreference)
					setSummary((RingtonePreference)preference, (String)newValue);

			}else if(newValue instanceof Boolean){
				setSummary((CheckBoxPreference)preference, (Boolean)newValue);
			}
			return true;
		}
		return false;
	}

	// Summaryを設定（リスト）
	public void setSummary(ListPreference lp, String param)
	{
		if(param == null){
			lp.setSummary("未設定");
		}else{
			lp.setSummary(param);
		}
		param = null;
	}

	// Summaryを設定（エディットテキスト）
	private void setSummary(EditTextPreference ep, String param) {

		if(param == null){
			ep.setSummary("未設定");
		}else{
			ep.setSummary(param);
		}
		param = null;
	}

	// Summaryを設定（リングトーン）
	private void setSummary(RingtonePreference rp, String param) {

		if(param == null){
			rp.setSummary("Unselected");
		}else{
			rp.setSummary("Selected「" + param + "」");
		}
		param = null;
	}

	// Summaryを設定（チェックボックス）
	public void setSummary(CheckBoxPreference cp, Boolean param){

		cp.setSummary("Selected「" + param + "」");
		param = false;
	}

	// Activity破棄時に実行
	public void onDestroy(){
		super.onDestroy();
		
		get_num.setOnPreferenceChangeListener(null);
		get_num = null;
	}

	// Activityの再開時に実行
	public void onRestart(){
		super.onRestart();
		
		get_num.setEnabled(true);
	}

	// Activityの停止時に実行
	public void onStop(){
		super.onStop();
		
		get_num.setEnabled(false);
	}

}