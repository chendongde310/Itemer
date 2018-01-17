package cn.com.cdgame.itemer.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.cdgame.itemer.Attribute;
import cn.com.cdgame.itemer.R;

/**
 * 作者：陈东
 * 日期：2018/1/15 - 下午3:53
 * 注释：
 */
public class ABAdapter extends BaseQuickAdapter<Attribute,BaseViewHolder> {

    int KEY = R.id.key;
    int VALUE = R.id.value;
    int DELETE = R.id.delete;



    public ABAdapter() {
        super(R.layout.item);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final Attribute item) {
        helper.setOnClickListener(KEY, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title("选取属性")
                        .items(R.array.items_attribute)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                helper.setText(KEY,text);
                                item.setKey(text.toString());
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

        ((EditText)(helper.getView(VALUE))).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setValue(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
