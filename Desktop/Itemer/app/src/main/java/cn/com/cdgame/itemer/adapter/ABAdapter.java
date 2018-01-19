package cn.com.cdgame.itemer.adapter;

import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.cdgame.itemer.Item;
import cn.com.cdgame.itemer.R;

/**
 * 作者：陈东
 * 日期：2018/1/15 - 下午3:53
 * 注释：属性
 */
public class ABAdapter extends BaseQuickAdapter<Item.Attributes, BaseViewHolder> {

    int KEY = R.id.attribute;
    int DELETE = R.id.delete;


    public ABAdapter() {
        super(R.layout.item);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final Item.Attributes attribute) {
        if (!TextUtils.isEmpty(attribute.getAttributes()))
            helper.setText(KEY, attribute.getAttributes());

        helper.setOnClickListener(KEY, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title("选取属性")
                        .items(R.array.items_attribute)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                helper.setText(KEY, text);
                                attribute.setAttributes(text.toString());
                                attribute.setMId(which);
                            }
                        })
                        .show();
            }
        });
        helper.setOnClickListener(DELETE, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(helper.getAdapterPosition());
            }
        });

    }
}
