package kr.elab.android.lib.dynamiclistview;

import android.view.View;
import android.view.ViewGroup;

public interface DynamicAdapter<T> {
    public String getPageUrl(long pageId);
    public void getPageData(String url, DynamicCallback<T> callback);
    public View getView(T obj, View view, ViewGroup viewGroup);
}
