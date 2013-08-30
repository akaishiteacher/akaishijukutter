package net.akaishi_teacher.akaishijukutter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TweetActivity extends Activity
{

	private EditText mInputText;
	private Twitter mTwitter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);

		mTwitter = TwitterUtils.getTwitterInstance(this);

		mInputText = (EditText) findViewById(R.id.tweet);

		findViewById(R.id.action_tweet).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tweet();
			}
		});
	}
	private void tweet()
	{
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>()
		{
			@Override
			protected Boolean doInBackground(String... params)
			{
				try {
					mTwitter.updateStatus(params[0]);
					return true;
				} catch (TwitterException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result)
			{
				if (result) {
					showToast("イケたで工藤");
					finish();
				} else {
					showToast("アカンで工藤");
				}
			}
		};
		task.execute(mInputText.getText().toString());
	}

	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}


}
