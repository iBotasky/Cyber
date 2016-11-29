package com.botasky.cyberblack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class RefreshRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mTitles = null;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOT = 1;

    private int load_more_status = 0;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;

    public RefreshRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mTitles = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            int index = i + 1;
            mTitles.add("item" + index);
        }
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final View view = mInflater.inflate(R.layout.item_girls_layout, parent, false);
//        //这边可以做一些属性设置，甚至事件监听绑定
//        //view.setBackgroundColor(Color.RED);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
        View view;
        if (viewType == TYPE_ITEM) {
            view = mInflater.inflate(R.layout.item_girls_layout, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOT) {
            view = mInflater.inflate(R.layout.item_girls_footer, parent, false);
            FootViewHolder footViewHolder = new FootViewHolder(view);
            return footViewHolder;

        }
        return null;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
//            ((ItemViewHolder) holder).girls_iv.setText(mTitles.get(position));
            ImageUtil.displayImageByUrl(mContext, "http://ww2.sinaimg.cn/large/610dc034jw1f9vyl2fqi0j20u011habc.jpg", ((ItemViewHolder) holder).girls_iv);
            holder.itemView.setTag(position);
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        //增加了Foot所以+1
        return mTitles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount())
            return TYPE_FOOT;
        else
            return TYPE_ITEM;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView girls_iv;

        public ItemViewHolder(View view) {
            super(view);
            girls_iv = (ImageView) view.findViewById(R.id.girls_iv);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {
        public TextView foot_view_item_tv;

        public FootViewHolder(View itemView) {
            super(itemView);
            foot_view_item_tv = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
        }
    }


    //下拉添加数据
    public void addItem(List<String> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(mTitles);
        mTitles.removeAll(mTitles);
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    //上滑加载数据
    public void addMoreItem(List<String> newDatas) {
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    /**
     * 改变滑动状态
     *
     * @param status
     */
    public void changeLoadStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

}
