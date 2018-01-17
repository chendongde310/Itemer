package cn.com.cdgame.itemer

import java.io.Serializable

/**
 * 作者：陈东
 * 日期：2018/1/15 - 上午10:41
 * 注释：
 */
class Item : Serializable{
    var name = "未命名"
    var type = ""
    var lv = ""
    var rank = ""
    var describe = ""
    var attributes = ArrayList<Attribute>()
    var size = 0
    var affix = 0

    //-------------
    private var index = 0

    /**
     * 添加属性
     * 0是随机
     * 如果是百分比属性，传入5则是5%2
     */


    fun setAttributes(datas:List<Attribute>) {
        attributes.clear()
        attributes.addAll(datas)
    }


}

/**
 * 0是随机属性／随机值
 */
class Attribute(mKey: String, mValue: String) : Serializable{
    var  key = mKey
    var value = mValue
}





