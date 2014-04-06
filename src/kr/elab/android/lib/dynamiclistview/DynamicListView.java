package kr.elab.android.lib.dynamiclistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DynamicListView extends ListView {
    private DynamicListViewAdapter adapter;

    public DynamicListView(Context context) {
        this(context, null);
    }

    public DynamicListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.adapter = DynamicListViewAdapter.setListViewAdapter(this);
    }

    public void setDynamicAdapter(DynamicAdapter adapter) {
        this.adapter.setDynamicAdapter(adapter);
    }
}
