package cn.com.cdgame.itemer.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import cn.com.cdgame.itemer.Item;
import cn.com.cdgame.itemer.R;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2018/1/16 - 上午10:00
 * 注释：
 */
public class ItemAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {


    public ItemAdapter(@Nullable List<Item> data) {
        super(R.layout.item_list, data);
    }
    public ItemAdapter() {
        super(R.layout.item_list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Item item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.type,item.getType());
        helper.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(helper.getAdapterPosition() - 1);
                Hawk.delete("DATAS");
                Hawk.put("DATAS", getData());
            }
        });
    }


}
