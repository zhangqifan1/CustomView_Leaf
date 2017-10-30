package com.bawei.customview_leaf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张祺钒
 * on2017/10/30.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>  {
    private List<HashMap<String, String>> dataList = new ArrayList<>();
    private HashMap<String, Boolean> map = new HashMap<>();
    private Context context;
    public IRefresh iRefresh;
    public void setiRefresh(IRefresh iRefresh) {
        this.iRefresh = iRefresh;
    }

    public Adapter(List<HashMap<String, String>> list, Context context) {
        this.dataList = list;
        this.context = context;

        //cb标识
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                map.put(dataList.get(i).get("id"), false);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //首先判空
        if (dataList.size() > 0) {
            String id = dataList.get(position).get("id");
            holder.cb.setChecked(map.get(id));
            iRefresh.refreshIsChecked(selectAll());
            iRefresh.refreshPrice(map);
        }
        //商品信息
        final HashMap<String, String> GoodsInfoHashMap = dataList.get(position);

        holder.tvContent.setText("商品:"+GoodsInfoHashMap.get("name")+"\n"+"商品单价:"+GoodsInfoHashMap.get("price"));
        holder.tvTotal.setText(GoodsInfoHashMap.get("count")+"件,共计:"+Integer.parseInt(GoodsInfoHashMap.get("price"))*Integer.parseInt(GoodsInfoHashMap.get("count"))+"元");

        holder.butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.remove(GoodsInfoHashMap.get("id"));
                dataList.remove(position);
                notifyDataSetChanged();
                iRefresh.refreshPrice(map);
            }
        });
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cb.isChecked()){
                    map.put(GoodsInfoHashMap.get("id"),true);
                }else{
                    map.put(GoodsInfoHashMap.get("id"),false);
                }
                iRefresh.refreshIsChecked(selectAll());
                iRefresh.refreshPrice(map);
            }
        });

        holder.abbsub.setListener(new PlusReduceView.Listener() {
            @Override
            public void changed(String string) {
                GoodsInfoHashMap.put("count",string);
                holder.tvTotal.setText(GoodsInfoHashMap.get("count")+"件,共计:"+Integer.parseInt(GoodsInfoHashMap.get("price"))*Integer.parseInt(GoodsInfoHashMap.get("count"))+"元");
//                notifyDataSetChanged();
            }
        });
    }

    public void setMap(HashMap<String, Boolean> map) {
        this.map.putAll(map);
        notifyDataSetChanged();
    }

    public HashMap<String, Boolean> getMap() {
        return map;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cb;
        private final PlusReduceView abbsub;
        private final Button butDelete;
        private final TextView tvContent;
        private final TextView tvTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
            abbsub = itemView.findViewById(R.id.abbsub);
            butDelete = itemView.findViewById(R.id.butDelete);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }

    //全选方法
    public boolean selectAll() {
        boolean isChecked = true;
        //遍历标识
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getValue() == false) {
                isChecked = false;
                break;
            }
        }
        return isChecked;
    }
}
