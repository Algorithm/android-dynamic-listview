Android Dynamic Listview
========================

```java
DynamicListViewAdapter adapter = DynamicListViewAdapter.setListViewAdapter((ListView)findViewById(R.id.testlist));
adapter.setDynamicAdapter(new DummyDynamicAdapter());
```

```java
public class DummyDynamicAdapter implements DynamicAdapter<String> {
    @Override
    public String getPageUrl(long pageId) {
        return String.format("http://test.elab.kr/page/%d", pageId);
    }

    @Override
    public void getPageData(String url, DynamicCallback<String> callback) {
        Log.d("getPageData", url);
        if(url.equals("http://test.elab.kr/page/300")) {
            callback.addItem(new ArrayList<String>());
            return;
        }

        ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i<10; i++)
            list.add("test " + url + " " + i);

        callback.addItem(list);
    }

    @Override
    public View getView(String obj, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, null);
        }
        ((TextView)view.findViewById(R.id.test_id)).setText(obj);
        return view;
    }
}
```
