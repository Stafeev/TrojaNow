package com.example.trojanow;

import java.util.List;

import com.example.post.Post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PostAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<Post> listPost;

    public PostAdapter(Context context, List<Post> listOfPosts) {
        this.context = context;
        this.listPost = listOfPosts;
    }

    public int getCount() {
        return listPost.size();
    }

    public Object getItem(int position) {
        return listPost.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Post entry = listPost.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_post_list, null); // this will inflate the activity_post_list.xml for posts
        }
        TextView postMsg = (TextView) convertView.findViewById(R.id.postMsg);
        postMsg.setText(entry.getPostMessage());

        TextView byUser = (TextView) convertView.findViewById(R.id.byUser);
        byUser.setText(entry.getUser());

        TextView temp = (TextView) convertView.findViewById(R.id.temp);
        temp.setText(entry.getTemp());
        
        TextView postPK = (TextView) convertView.findViewById(R.id.postPK);
        postPK.setText(""+entry.getPostID());

        // Set the onClick Listener on this button
       /* Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
        btnRemove.setFocusableInTouchMode(false);
        btnRemove.setFocusable(false);
        btnRemove.setOnClickListener(this);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        btnRemove.setTag(entry);*/

        // btnRemove.setId(position);
        

        return convertView;
    }

    @Override
    public void onClick(View view) {
    	/*Post entry = (Post) view.getTag();
        listPost.remove(entry);
        // listPhonebook.remove(view.getId());
        notifyDataSetChanged();*/

    }

    private void showDialog(Post entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }

}
