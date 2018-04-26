package io.qthjen_dev.notesandplans.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import io.qthjen_dev.notesandplans.Model.ImageModel;
import io.qthjen_dev.notesandplans.R;

public class ListImageAdapter extends BaseAdapter {

    private List<ImageModel> list;
    private Context context;

    public ListImageAdapter(List<ImageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {

        ImageView image;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if ( convertView == null ) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_image, null);
            holder.image = convertView.findViewById(R.id.iv_imageList);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(convertView).load(list.get(position).getUri()).into(holder.image);

        return convertView;
    }

}
