package cn.chaboshi.cbslibrary.adapter.cell;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by testcbs on 2017/5/25.
 */

public class RecyclerHolder<T extends View> extends RecyclerView.ViewHolder{

    private T t;

    public RecyclerHolder(T itemView) {
        super(itemView);
        this.t = itemView;
    }

    public T getBaseCell() {
        return t;
    }
}
