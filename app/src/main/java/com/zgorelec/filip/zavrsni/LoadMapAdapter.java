package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class LoadMapAdapter extends RecyclerView.Adapter<LoadMapAdapter.MapViewHolder> implements IOnItemClickListener {


    private Context mCtx;
    private static ClickListener clickListener;
    private List<String> mapNames;


    public List<String> getMapList() {
        return mapNames;
    }

    public LoadMapAdapter(Context mCtx, List<String> maps) {
        this.mCtx = mCtx;
        this.mapNames = maps;
    }

    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.map_card_layout, null);
        return new MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MapViewHolder holder, int position) {
        holder.mapNameTv.setText(mapNames.get(position));
    }


    @Override
    public int getItemCount() {
        return mapNames.size();
    }


    class MapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mapNameTv;

        public MapViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mapNameTv = itemView.findViewById(R.id.mapName);
            mapNameTv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LoadMapAdapter.clickListener = clickListener;
    }

    public void clear() {
        mapNames.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<String> list) {
        mapNames.addAll(list);
        notifyDataSetChanged();
    }

}
