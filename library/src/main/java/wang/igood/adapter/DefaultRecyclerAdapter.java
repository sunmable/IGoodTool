package cn.chaboshi.cbslibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.chaboshi.cbslibrary.adapter.cell.BaseCell;
import cn.chaboshi.cbslibrary.adapter.cell.BaseRecyclerHolder;
import cn.chaboshi.cbslibrary.adapter.cell.RecyclerHolder;
import cn.chaboshi.cbslibrary.domain.BaseDomain;
import cn.chaboshi.cbslibrary.handler.IDataChangeListener;

/**
 * Created by testcbs on 2017/5/25.
 */

public class DefaultRecyclerAdapter<T extends BaseDomain> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_HEADVIEW=100;
    private final static int TYPE_ITEM=101;

    private List<T> data = new ArrayList<T>();
    private Context context;
    private IDataChangeListener dataChangeListener;
    private int cellResId;
    private View mHeadView;

    public DefaultRecyclerAdapter( int cellResId, IDataChangeListener dataChangeListener, Context context) {
        this.context = context;
        this.cellResId = cellResId;
        this.dataChangeListener = dataChangeListener;
    }


    public void setmHeadView(View mHeadView) {
        this.mHeadView = mHeadView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_HEADVIEW){
            RecyclerHolder headViewHolder=new RecyclerHolder(mHeadView);
            return headViewHolder;
        }

        BaseCell content = (BaseCell) LayoutInflater.from(parent.getContext()).inflate(cellResId,null);
        content.initView();
        content.config();
        BaseRecyclerHolder baseRecyclerCell = new BaseRecyclerHolder(content);
        return baseRecyclerCell;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  BaseRecyclerHolder){
            BaseRecyclerHolder baseRecyclerHolder = (BaseRecyclerHolder) holder;
            baseRecyclerHolder.getBaseCell().setTag(position);
            baseRecyclerHolder.getBaseCell().resetCell(data.get(position),data,dataChangeListener);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadView!=null){
            if(position==0){
                return TYPE_HEADVIEW;
            }else {
                return TYPE_ITEM;
            }
        }else {
                return TYPE_ITEM;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
