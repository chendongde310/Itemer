package cn.com.cdgame.itemer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.com.cdgame.itemer.adapter.ABAdapter;

public class MainActivity extends AppCompatActivity {

    ABAdapter adapter;
    Item data = new Item();
    private android.widget.EditText name;
    private android.widget.TextView type;
    private TextView rank;

    private android.widget.AutoCompleteTextView describe;
    private android.support.v7.widget.RecyclerView attributes;
    private Intent intent;
    private int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        this.attributes = (RecyclerView) findViewById(R.id.attributes);
        this.describe = (AutoCompleteTextView) findViewById(R.id.describe);

        this.rank = (TextView) findViewById(R.id.rank);
        this.type = (TextView) findViewById(R.id.type);
        this.name = (EditText) findViewById(R.id.name);
        if (intent.getSerializableExtra("data") != null) {
            data = (Item) intent.getSerializableExtra("data");
            name.setText(data.getName());
            describe.setText(data.getDescribe());
            rank.setText(data.getRank());
            type.setText(data.getType());
            name.setText(data.getName());
            position = intent.getIntExtra("position",-1);
        }
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title("选取装备品质")
                        .items(R.array.items_rank)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                rank.setText(text);
                                data.setRank(text.toString());
                            }
                        })
                        .show();
            }
        });
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title("选取装备类型")
                        .items(R.array.items_type)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                type.setText(text);
                                data.setType(text.toString());
                            }
                        })
                        .show();
            }
        });

        attributes.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ABAdapter();
        if (data.getAttributes().size() > 0) {
            adapter.addData(data.getAttributes());
        }
        View view = View.inflate(this, R.layout.add_item, null);
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getItemCount() < 10) {
                    adapter.addData(new Item.Attributes("",0));
                }
            }
        });
        adapter.addFooterView(view);
        attributes.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
                if (name.getText() == null) {
                    return true;
                }
                data.setName(name.getText().toString());
                data.setDescribe(TextUtils.isEmpty(describe.getText()) ? "—— 由全城最\"棒\"的铁匠塞弗打造" : describe.getText().toString());
                data.setSize(adapter.getItemCount());
                data.setAttributes(adapter.getData());
                intent.putExtra("data", data);
                setResult(position, intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
