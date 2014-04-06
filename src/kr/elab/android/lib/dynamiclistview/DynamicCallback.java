package kr.elab.android.lib.dynamiclistview;

import java.util.List;

public interface DynamicCallback<T> {
    public void addItem(List<T> items);
}
