package com.example.car;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCar_Adapter extends RecyclerView.Adapter<MyCar_Adapter.Car_ViewHolder> {

    private ArrayList<Car> cars;
    private OnReclerViewItemClickListener listener;

    public MyCar_Adapter(ArrayList<Car> cars, OnReclerViewItemClickListener listener) {
        this.cars = cars;
        this.listener = listener;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    @Override
    public Car_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout, null, false);
        Car_ViewHolder viewHolder = new Car_ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyCar_Adapter.Car_ViewHolder holder, int position) {
        Car c = cars.get(position);
        if (c.getImage() != null && !c.getImage().isEmpty())
            holder.image.setImageURI(Uri.parse(c.getImage()));
            else{
                holder.image.setImageResource(R.drawable.pngwing);
            }
            holder.tv_model.setText(c.getModel());
            holder.tv_color.setText(c.getColor());
            try {
                holder.tv_color.setTextColor(Color.parseColor(c.getColor())); // تحديد الللون حسب رمزة في الهكسا ديسيمال
            }catch (Exception e){

            }

            holder.tv_dbl.setText(String.valueOf(c.getDbl()));
            // holder.image.setTag(c.getId()); // or // معناه انه بيخزن اي عنصر مخفي جوه العنصر الي بدك ياه
            holder.id = c.getId(); // or

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class Car_ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tv_model, tv_color, tv_dbl;
        int id;

        public Car_ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.custem_car_iv);
            tv_model = itemView.findViewById(R.id.custem_car_tv_model);
            tv_color = itemView.findViewById(R.id.custem_car_tv_color);
            tv_dbl = itemView.findViewById(R.id.custem_car_tv_debl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   int id = (int) image.getTag();  // هاد يعني انه لما يضغط على الصورة اعرض البيانات ( بمعني اعرض اللسته الاخرى )
                    listener.onItemClick(id); // or
                }
            });
        }

    }
}
