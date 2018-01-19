package cn.com.cdgame.itemer

import java.io.Serializable

/**
 * 作者：陈东
 * 日期：2018/1/15 - 上午10:41
 * 注释：
 */
class Item : Serializable {
    var name = "未命名"
    var type = "0"
    var rank = "0"
    var describe = ""
    var attributes = ArrayList<Attributes>() //必带属性
    var size = 0 //随机魔法属性数量

    //-------------
    private var index = 0

    /**
     * 添加属性
     * 0是随机
     * 如果是百分比属性，传入5则是5%2
     */


    fun setAttributes(datas: List<Attributes>) {
        attributes.clear()
        attributes.addAll(datas)
    }

    class Attributes(mAttributes: String, mId: Int) : Serializable {
        var mId = mId
        var attributes = mAttributes
    }


}






