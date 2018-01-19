package cn.com.cdgame.itemer.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import cn.com.cdgame.itemer.Item;
import cn.com.cdgame.itemer.R;
import cn.com.cdgame.itemer.utils;

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
    protected void convert(final BaseViewHolder helper, final Item item) {
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
        helper.getView(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "已经复制到剪切板", Toast.LENGTH_SHORT).show();
                ClipboardManager cm = (ClipboardManager)mContext. getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(utils.code(item));
            }
        });
    }


}
