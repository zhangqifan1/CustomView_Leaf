package com.bawei.customview_leaf;

import java.util.Map;

/**
 * Created by 张祺钒
 * on2017/10/30.
 * 自定义接口  主要是适配器与MainActivity的几个控件进行关联
 *      比如 全选  比如价格的变化
 */

public interface IRefresh {
    void refreshPrice(Map<String,Boolean> map);
    void refreshIsChecked(boolean b);
}
