package Classe;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterPlantas extends ArrayAdapter<Planta> {
    private Context mcontext;
    int mresource;
    private  static  class  ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    public ListAdapterPlantas(Context context, int resource, ArrayList<Planta>obPlantas){
        super(context,resource,obPlantas);
        mcontext = context;
        mresource = resource;
    }

//    @Override
//    public View getView(int position,  View convertView,  ViewGroup parent) {
//        byte[] img= getItem(position).getFoto();
//        String nome= getItem(position).getNomePlanta();
//        Planta planta= new Planta(nome,img);
//        View result;
//        ViewHolder holder;
//        if(convertView == null){
//            LayoutInflater layoutInflater= LayoutInflater.from(mcontext);
//            convertView = layoutInflater.inflate(mresource,parent,false);
//            holder = new ViewHolder();
//            holder.imageView= (ImageView)convertView.findViewById(R.id.imageViewPlantas);
//            holder.textView = (TextView) convertView.findViewById(R.id.textViewNPlants);
//            result =  convertView;
//            convertView.setTag(holder);
//
//        }else {
//            holder = (ViewHolder)convertView.getTag();
//            result = convertView;
//        }
//        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(planta.getFoto(),0,planta.getFoto().length));
//        holder.textView.setText(planta.getNomePlanta());
//        return  convertView;
//
//
//
//    }
}
