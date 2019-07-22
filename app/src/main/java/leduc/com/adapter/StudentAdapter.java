package leduc.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import leduc.com.hocmenucontext.R;
import leduc.com.model.Student;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private int resource;

    public StudentAdapter(Context context, int resource) {
        super(context, resource);
        this.context     = context;
        this.resource    = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
            //View view = this.context.getLayoutInflater().inflate(this.resource, null);

            holder = new ViewHolder();

            holder.imgIcon          = (ImageView) convertView.findViewById(R.id.imgIcon);
            holder.txtYouCode       = (TextView) convertView.findViewById(R.id.txtYouCode);
            holder.txtStudentName   = (TextView) convertView.findViewById(R.id.txtStudentName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Student st = getItem(position);
        holder.txtYouCode.setText(st.getCode().toString());
        holder.txtStudentName.setText(st.getName().toString());
        if (st.getSex()) {
            holder.imgIcon.setImageResource(R.drawable.one);
        } else {
            holder.imgIcon.setImageResource(R.drawable.woman);
        }

        return convertView;
    }

    private class ViewHolder {
        private ImageView imgIcon;
        private TextView txtYouCode;
        private TextView txtStudentName;
    }
}
