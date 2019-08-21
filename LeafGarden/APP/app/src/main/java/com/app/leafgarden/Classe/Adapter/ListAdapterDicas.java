package com.app.leafgarden.Classe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.leafgarden.R;

import java.util.List;

public class ListAdapterDicas  extends ArrayAdapter<String> {
    private Context mcontext;
    int mresource;

    private static class ViewHolder{
        TextView textView;
    }

    public ListAdapterDicas(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource= resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String stringAux = getItem(position);
        View result;
        ViewHolder viewHolder;


        if(convertView == null){
            LayoutInflater layoutInflater= LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(mresource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textViewDicas);
            result =  convertView;
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ListAdapterDicas.ViewHolder)convertView.getTag();
            result = convertView;
        }
        viewHolder.textView.setText(stringAux);
        return  convertView;



    }
}
