package net.akaishi_teacher.akaishijukutter;

import twitter4j.DirectMessage;
import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class DMAdapter extends ArrayAdapter<DirectMessage>
{
	private LayoutInflater mInflater;
	
	public DMAdapter(Context context)
    {
	    super(context, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent)
	{
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_dm, null);
        }
        DirectMessage item = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(item.getSender().getName());
        TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
        screenName.setText("@" + item.getSender().getScreenName());
        TextView text = (TextView) convertView.findViewById(R.id.text);
        text.setText(item.getText());
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        timestamp.setText(DateFormat.format("MM/dd kk:mm:ss", item.getCreatedAt()));
        SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
        icon.setImageUrl(item.getSender().getProfileImageURL());
        return convertView;
    }
}
