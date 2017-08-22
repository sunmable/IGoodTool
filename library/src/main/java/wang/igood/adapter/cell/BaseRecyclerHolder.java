package cn.chaboshi.cbslibrary.adapter.cell;

import android.support.v7.widget.RecyclerView;

/**
 * Created by testcbs on 2017/5/25.
 */

public class BaseRecyclerHolder<T extends BaseCell> extends RecyclerView.ViewHolder{

    private T t;

    public BaseRecyclerHolder(T itemView) {
        super(itemView);
        this.t = itemView;
    }

    public T getBaseCell() {
        return t;
    }
}
