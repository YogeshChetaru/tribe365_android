package com.chetaru.tribe365_new.Adapter.freeVersion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.freeVersion.HappyIndexMonthly;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.List;

public class Ad_HICalender extends RecyclerView.Adapter<Ad_HICalender.ViewHolder> {
    List<HappyIndexMonthly> list;
    Context mContext;
    Utility utility;
    String monthStart;
    onListenerClick mListener;
    List<String> notWorkingDays;

    public Ad_HICalender(List<String> notWorkingDays,List<HappyIndexMonthly> list,String monthStart,Context context,onListenerClick mListener){
        this.list=list;
        this.monthStart=monthStart;
        this.mContext=context;
        this.mListener=mListener;
        this.utility = new Utility();
        this.notWorkingDays=notWorkingDays;

        if (notWorkingDays.size()>0){
            for(int i=0;i<notWorkingDays.size();i++){
                String avc= notWorkingDays.get(i);
                Log.d("getDays",avc.toString());
            }
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_calender,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // Glide.with(mContext).load(mContext.getDrawable(list.get(position).getDrawbleImage())).into(holder.faceShowImage);
        if (monthStart.contains("Monday")){
            if (position==0){
                holder.main_ll.setVisibility(View.GONE);

            }else {
                holder.main_ll.setVisibility(View.VISIBLE);
                holder.countText.setText(list.get(position).getDate().toString());
                if (list.get(position).getScore() != null)
                    setImageRange(list.get(position).getScore(), holder.faceShowImage);
            }

        }else if (monthStart.contains("Tuesday")){
            if (position<2){
                holder.main_ll.setVisibility(View.GONE);
            }else {
                holder.main_ll.setVisibility(View.VISIBLE);
                holder.countText.setText(list.get(position).getDate());
                if (list.get(position).getScore()!=null)
                    setImageRange(list.get(position).getScore(),holder.faceShowImage);
            }
        }else if (monthStart.contains("Wednesday")){
            if (position < 3) {
                holder.main_ll.setVisibility(View.GONE);
            }else {
                holder.main_ll.setVisibility(View.VISIBLE);
                holder.countText.setText(list.get(position).getDate());
                if (list.get(position).getScore()!=null)
                    setImageRange(list.get(position).getScore(),holder.faceShowImage);
            }
        }else if (monthStart.contains("Thursday")){
            if (position<4){
                holder.main_ll.setVisibility(View.GONE);
            }else {
                holder.main_ll.setVisibility(View.VISIBLE);
                holder.countText.setText(list.get(position).getDate());
                if (list.get(position).getScore()!=null)
                    setImageRange(list.get(position).getScore(),holder.faceShowImage);
            }
        }else if (monthStart.contains("Friday")){
            if (position<5){
                holder.main_ll.setVisibility(View.GONE);
            }else{
                holder.main_ll.setVisibility(View.VISIBLE);
                holder.countText.setText(list.get(position).getDate());
                if (list.get(position).getScore()!=null)
                setImageRange(list.get(position).getScore(),holder.faceShowImage);
            }
        }else if (monthStart.contains("Saturday")){
            if (position<6){
                holder.main_ll.setVisibility(View.GONE);
            }else {
                holder.main_ll.setVisibility(View.VISIBLE);
                holder.countText.setText(list.get(position).getDate());
                if (list.get(position).getScore()!=null);
                setImageRange(list.get(position).getScore(),holder.faceShowImage);
            }
        }else {

            holder.main_ll.setVisibility(View.VISIBLE);
            holder.countText.setText(list.get(position).getDate());
            if (list.get(position).getScore()!=null)
                setImageRange(list.get(position).getScore(),holder.faceShowImage);

        }
        if (notWorkingDays.size()>0){
            for (int i=0 ;i<notWorkingDays.size();i++){

                if ((position % 7)==0){
                    if (notWorkingDays.get(0).equals("sunday")){
                        holder.countText.setTextColor(mContext.getResources().getColor(R.color.red));
                        holder.faceShowImage.setImageResource(R.drawable.ic_dash);
                    }else if (notWorkingDays.size()>1 &&notWorkingDays.get(1).equals("sunday")){
                        holder.countText.setTextColor(mContext.getResources().getColor(R.color.red));
                        holder.faceShowImage.setImageResource(R.drawable.ic_dash);

                    }else {
                        holder.main_ll.setVisibility(View.VISIBLE);
                        holder.countText.setText(list.get(position).getDate());
                        if (list.get(position).getScore()!=null)
                            setImageRange(list.get(position).getScore(),holder.faceShowImage);
                    }
                }else if ((position +1) % 7==0){
                    if (notWorkingDays.get(0).equals("saturday")){
                        holder.countText.setTextColor(mContext.getResources().getColor(R.color.red));
                        holder.faceShowImage.setImageResource(R.drawable.ic_dash);
                    }else if (notWorkingDays.size()>1 &&notWorkingDays.get(1).equals("saturday")){
                        holder.countText.setTextColor(mContext.getResources().getColor(R.color.red));
                        holder.faceShowImage.setImageResource(R.drawable.ic_dash);

                    }else {
                        holder.main_ll.setVisibility(View.VISIBLE);
                        holder.countText.setText(list.get(position).getDate());
                        if (list.get(position).getScore()!=null)
                            setImageRange(list.get(position).getScore(),holder.faceShowImage);

                    }
                }
            }

        }else{
            holder.main_ll.setVisibility(View.VISIBLE);
            holder.countText.setText(list.get(position).getDate());
            if (list.get(position).getScore()!=null)
                setImageRange(list.get(position).getScore(),holder.faceShowImage);

        }

        try {


            //int numColumns = ((GridView) viewGroup).getNumColumns();
            int numColumns = 7;
            boolean isLast = (position % 7) == 0;

           /* if ((position ==0 || position % 7 ==0 || (position+1) % 7 ==0)){
                holder.countText.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.faceShowImage.setImageResource(R.drawable.ic_dash);
            }else {
                holder.countText.setTextColor(mContext.getResources().getColor(R.color.black));
            }*/

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            holder.main_ll.setOnClickListener(v -> {
                if (list.get(position).getScore() !=null ) {
                    if ((position ==0 || position % 7 ==0 || (position+1) % 7 ==0)){
                    }else {
                        mListener.onHIClick(position, list.get(position));
                    }

                    if (notWorkingDays.size()>0){
                        for (int i=0 ;i<notWorkingDays.size();i++){

                            if ((position % 7)==0){
                                if (notWorkingDays.get(0).equals("sunday")){
                                    break;
                                }else if (notWorkingDays.size()>1 &&notWorkingDays.get(1).equals("sunday")){
                                    break;
                                }else {
                                    mListener.onHIClick(position, list.get(position));
                                }
                            }else if ((position +1) % 7==0){
                                if (notWorkingDays.get(0).equals("saturday")){
                                    break;
                                }else if (notWorkingDays.size()>1 &&notWorkingDays.get(1).equals("saturday")){
                                    break;
                                }else {
                                    mListener.onHIClick(position, list.get(position));

                                }
                            }
                        }

                    }else{
                        mListener.onHIClick(position, list.get(position));
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void setImageRange(Double finalCount, ImageView imageFace){
        try {
            if (finalCount >= 0 && finalCount <= 30) {
                imageFace.setImageResource(R.drawable.low);
            } else if (finalCount >= 31 && finalCount <= 60) {
                imageFace.setImageResource(R.drawable.medium);
            } else if (finalCount >= 60) {
                imageFace.setImageResource(R.drawable.high);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        try{
            return list.size();
        }catch (Exception e) {
            return 0;
        }
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView faceShowImage;
        TextView countText;
        LinearLayout main_ll;
        public ViewHolder(@NonNull View v){
            super(v);
            faceShowImage=v.findViewById(R.id.face_image);
            countText=v.findViewById(R.id.count_txt);
            main_ll=v.findViewById(R.id.main_ll);
        }
    }

    public interface onListenerClick{
       public void onHIClick(int position,HappyIndexMonthly value);
    }

}
