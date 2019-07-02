package com.brycegao.dragview;

import android.widget.BaseAdapter;
import java.util.List;

public abstract class DragAdapter extends BaseAdapter {
  private List<String> data;

  public DragAdapter(List<String> data) {
    this.data = data;
  }

  /**
   * 交换位置
   *
   * @param start
   * @param end
   */
  public void change(int start, int end) {
    String srcData = data.get(start);
    data.remove(srcData);
    data.add(end, srcData);
    notifyDataSetChanged();
  }
}
