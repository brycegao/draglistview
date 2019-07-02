package com.brycegao.draglistviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.brycegao.dragview.DragAdapter;
import com.brycegao.dragview.DragCallBack;
import com.brycegao.dragview.DragListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private DragListView dragListView;
    private MyAdapter dragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dragListView = findViewById(R.id.draglistview);
     //   dragListView.setDragViewId(R.id.drag_list_item_image);
        List<String> data = initData();
        dragAdapter = new MyAdapter(data, this, new IDeleteItem() {
            @Override public void deleteItem(int position) {
                dragAdapter.deleteItem(position);
            }
        });
        dragListView.setAdapter(dragAdapter);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Log.d("brycegao", "测试");
            }
        });

        dragListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("brycegao", "dddd");
            }
        });
    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("标题" + i);
        }
        return data;
    }

    private class MyAdapter extends DragAdapter {

        private List<String> data;
        private Context context;
        private IDeleteItem listener;

        public MyAdapter(List<String> data, Context context, IDeleteItem listener) {
            super(data);
            this.data = data;
            this.context = context;
            this.listener = listener;
        }

        public void deleteItem(int position) {
            data.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(context, R.layout.drag_list_item, null);
                holder.tvDrag = (TextView) convertView.findViewById(R.id.tv_drag);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvDelete = convertView.findViewById(R.id.tv_delete);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tvName.setText(data.get(position));

            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(context, "点击删除第" + position
                        + "条记录", Toast.LENGTH_LONG).show();
                    if (listener != null) {
                        listener.deleteItem(position);
                    }
                }
            });
            return convertView;
        }

        private class Holder {
            TextView tvName;
            TextView tvDrag;
            TextView tvDelete;
        }
    }

    private interface IDeleteItem {
        void deleteItem(int position);
    }

}
