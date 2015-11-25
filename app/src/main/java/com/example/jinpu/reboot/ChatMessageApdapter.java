package com.example.jinpu.reboot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import bean.chatmessage;

/**
 * Created by jinpu on 10/22/15.
 */
public  class ChatMessageApdapter extends BaseAdapter
{
    private LayoutInflater mInfalter;
    private List<chatmessage> mData;

    public ChatMessageApdapter(Context context,List<chatmessage> mDatas)
    {
       mInfalter = LayoutInflater.from(context);
       this.mData = mDatas;
    }
    public int getCount()
    {
        return mData.size();
    }
    public Object  getItem(int position)
    {
        return mData.get(position);
    }
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position,View convertView,ViewGroup parent)
    {
        chatmessage chatmsg = mData.get(position);
        ViewHolder viewholder = null;
        if(convertView == null)
        {
            if(getItemViewType(position) == 0) {
                convertView = mInfalter.inflate(R.layout.from_msg, parent, false);
                viewholder = new ViewHolder();
                viewholder.mData = (TextView) convertView.findViewById(R.id.id_from_msg_data);
                viewholder.mMsg = (TextView) convertView.findViewById(R.id.id_from_msg);
            }else{
                convertView = mInfalter.inflate(R.layout.to_msg, parent, false);
                viewholder = new ViewHolder();
                viewholder.mData = (TextView) convertView.findViewById(R.id.id_to_msg_data);
                viewholder.mMsg = (TextView) convertView.findViewById(R.id.id_to_msg);
            }
        convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        SimpleDateFormat myFmt=new SimpleDateFormat("H时mm分ss秒");
        viewholder.mData.setText(myFmt.format(chatmsg.getDate()));
        viewholder.mMsg.setText(chatmsg.getMsg());
        return convertView;
    }
    public int getItemViewType(int position)
    {
        chatmessage chatmsg = mData.get(position);
        if (chatmsg.getType() == chatmessage.Type.INCOMING)
        {
            return 0;
        }
        return 1;
    }
    public int getViewTypeCount()
    {
        return 2;
    }
    private final class ViewHolder
    {
        TextView mData;
        TextView mMsg;
    }
}
