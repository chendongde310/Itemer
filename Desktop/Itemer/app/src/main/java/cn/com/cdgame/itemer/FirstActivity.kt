package cn.com.cdgame.itemer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cn.com.cdgame.itemer.adapter.ItemAdapter
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    var datas: ArrayList<Item> = ArrayList()
    var adapter = ItemAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        datas.clear()
        if (Hawk.contains("DATAS")) {
            datas.addAll(Hawk.get<List<Item>>("DATAS"))
            adapter.addData(datas);
        }

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        val view = View.inflate(this, R.layout.header_item, null)
        adapter.setHeaderView(view)
        view.setOnClickListener {
            startActivityForResult(Intent(this, MainActivity::class.java), 9527)
        }
        adapter.setOnItemClickListener { adapter, view, position ->

            var i = Intent(this, MainActivity::class.java)
            i.putExtra("data", adapter.data.get(position) as Item)
            i.putExtra("position", position)
            startActivityForResult(i, 9528)

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val item = data.getSerializableExtra("data")
            if (requestCode == 9527) {
            } else if (requestCode == 9528) {
                if (resultCode != -1) {
                    adapter.remove(resultCode)
                }
            }
            if (item != null) {
                adapter.addData(0, item as Item)
                Hawk.delete("DATAS")
                Hawk.put("DATAS", adapter.data)
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.frist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.code -> {
                Toast.makeText(this, "老子还没做这个功能", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
