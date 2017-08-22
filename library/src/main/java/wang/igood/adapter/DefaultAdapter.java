package cn.chaboshi.cbslibrary.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.chaboshi.cbslibrary.adapter.cell.BaseCell;
import cn.chaboshi.cbslibrary.domain.BaseDomain;
import cn.chaboshi.cbslibrary.handler.IDataChangeListener;

/**
 * Created by testcbs on 2017/5/24.
 */
public class DefaultAdapter<T extends BaseDomain> extends BaseAdapter{

    private List<T> data = new ArrayList<T>();
    private Context context;
    private IDataChangeListener dataChangeListener;
    private int cellResId;

    public DefaultAdapter(@LayoutRes int cellResId, IDataChangeListener dataChangeListener, Context context) {
        this.context = context;
        this.cellResId = cellResId;
        this.dataChangeListener = dataChangeListener;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int i) {
        return this.data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try{
            if(view == null){
                BaseCell cell = (BaseCell) LayoutInflater.from(viewGroup.getContext()).inflate(cellResId,null);
                cell.initView();
                cell.config();
                view = cell;
            }
            BaseCell cell = (BaseCell) view;
            cell.setTag(i);
            cell.resetCell(data.get(i),data,dataChangeListener);
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
