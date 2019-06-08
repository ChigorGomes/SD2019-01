package Classe;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.leafgarden.R;

import java.util.ArrayList;

public class ListAdapterJardim extends ArrayAdapter<Jardim> {
    private Context mcontext;
    int mresource;

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public ListAdapterJardim(Context context, int resource, ArrayList<Jardim> obJardim) {
        super(context, resource, obJardim);
        mcontext = context;
        mresource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        byte[] img = getItem(position).getFoto();
        String nome = getItem(position).getNomePlanta();
        Planta planta = new Planta(nome, img);
        View result;
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewPlantas);
            holder.textView = (TextView) convertView.findViewById(R.id.textViewNPlants);
            result = convertView;
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(planta.getFoto(), 0, planta.getFoto().length));
        holder.textView.setText(planta.getNomePlanta());
        return convertView;


    }
}