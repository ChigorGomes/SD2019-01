package Classe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.leafgarden.R;

import java.util.ArrayList;

public class ListAdapterHistorico extends ArrayAdapter<Historico> {
    private Context mcontext;
    int mresource;

    private static class ViewHolder{
        TextView textView;
    }
    public ListAdapterHistorico(Context context, int resource, ArrayList<Historico> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource= resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String nome= getItem(position).getNomePlanta();
        String dataHorario= getItem(position).getDataHorario();
        Historico historico= new Historico(dataHorario,nome);
        View result;
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater layoutInflater= LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(mresource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textViewNamePlantaJardim);
            result =  convertView;
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ListAdapterHistorico.ViewHolder)convertView.getTag();
            result = convertView;
        }
        viewHolder.textView.setText(historico.getNomePlanta()+": "+historico.getDataHorario());
        return  convertView;



    }



}
