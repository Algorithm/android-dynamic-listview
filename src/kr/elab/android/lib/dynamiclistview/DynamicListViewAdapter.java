package kr.elab.android.lib.dynamiclistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DynamicListViewAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private class ItemData<T> {
        public long pageId;
        public T obj;

        public ItemData(long pageId, T obj) {
            this.pageId = pageId;
            this.obj = obj;
        }
    }

    private List<ItemData> pageData = new ArrayList<ItemData>();
    private int maxSaveItemCount = 100;
    private DynamicAdapter adapter;
    private boolean lock;
    private boolean footer;

    public static DynamicListViewAdapter setListViewAdapter(ListView list) {
        DynamicListViewAdapter nAdapter = new DynamicListViewAdapter();
        list.setAdapter(nAdapter);
        list.setOnScrollListener(nAdapter);
        return nAdapter;
    }

    public DynamicListViewAdapter() {
    }

    public DynamicListViewAdapter(DynamicAdapter adapter) {
        this.setDynamicAdapter(adapter);
    }

    public void setDynamicAdapter(DynamicAdapter adapter) {
        this.adapter = adapter;
        this.loadData(1);
    }

    @Override
    public int getCount() {
        return pageData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return adapter.getView(pageData.get(i).obj, view, viewGroup);
    }

    private void loadData(int p) { //0: 이전, 1:아래
        if(this.adapter == null || this.lock)
            return;

        long pageId = 0;
        if(pageData.size() > 0) {
            if(p == 0) {
                pageId = pageData.get(0).pageId - 1;
            } else if(p == 1) {
                pageId = pageData.get(pageData.size() - 1).pageId + 1;

                if(footer) pageId = -1;
            }
        }

        if(pageId == -1) {
            return;
        }

        lock = true;

        final long finalPageId = pageId;
        this.adapter.getPageData(this.adapter.getPageUrl(pageId), new DynamicCallback() {
            @Override
            public void addItem(List items) {
                if(items.size() == 0) {
                    footer = true;
                    return;
                }

                for(Object obj : items)
                    pageData.add(new ItemData(finalPageId, obj));

                /*
                if(pageData.size() > maxSaveItemCount) {
                    int removeCount = maxSaveItemCount / 6;
                    for(int i=0; i<removeCount; i++) {
                        pageData.remove(0);
                    }
                }
                */

                lock = false;
                DynamicListViewAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int count = totalItemCount - visibleItemCount;

        if(firstVisibleItem >= count && totalItemCount != 0)
        {
            loadData(1);
        }
    }
}
