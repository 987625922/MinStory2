package com.example.wind.minstory2.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wind.minstory2.R;

import java.util.List;


/**
 * Created by wind on 2016/5/27.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter {

    List<T> mDatas;
    private LayoutInflater mInflater;
    private int mLayout;
    private static final int TYPE_ITEM =0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    private int index;

    public RecyclerAdapter(Context context, List<T> mDatas,int mLayout){

        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
        this.mLayout = mLayout;

    }

    //点击事件
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    //绑定viewholder 设置显示的item里面的控件和控件的id和控件的内容
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemViewHolder itemViewHolder;
        FootViewHolder footViewHolder;
        if (viewType == TYPE_ITEM){
            index = TYPE_ITEM;
            itemViewHolder = new ItemViewHolder(mInflater.inflate(mLayout,
                    parent,false));
            return itemViewHolder;

        }else if (viewType == TYPE_FOOTER){
            index = TYPE_FOOTER;
            footViewHolder =
                    new FootViewHolder(mInflater.inflate(R.layout.item_more,parent,false));

            return footViewHolder;
        }

        return null;
    }


    //
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(position+1 < getItemCount()) {
            holder.itemView.setTag(position);


        }else if(position+1 == getItemCount()){



        }



       //设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }

    }
    //返回item的数量
    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }

    //布局控制
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }




    //下拉添加数据
    public void addItem(List<T> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(mDatas);
        mDatas.removeAll(mDatas);
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    //上拉添加数据
    public void loadAddItem(List<T> newDatas){
        mDatas.addAll(newDatas);
        notifyDataSetChanged();

    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
     class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView item_title;
        private TextView item_content;
        private ImageView img;
        public ItemViewHolder(View view){
            super(view);



           /*
            item_title = (TextView)view.findViewById(R.id.tv_item_main_title);
            item_content = (TextView)view.findViewById(R.id.tv_item_main_intro);
            img = (ImageView)view.findViewById(R.id.img_item_main);
            */
        }
    }

    /**
     * 底部FootView布局
     */
    class FootViewHolder extends  RecyclerView.ViewHolder{
        public FootViewHolder(View view) {
            super(view);

        }
    }


    public abstract void convert(RecyclerView.ViewHolder helper, T item, int position);


}
