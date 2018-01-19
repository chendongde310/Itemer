package cn.com.cdgame.itemer;

import java.util.List;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2018/1/16 - 下午3:01
 * 注释：
 */
public class utils {

    /**
     * @param code
     */
    private static String getType(int code) {
        switch (code) {
            case 1:
                return "头盔";
            case 2:
                return "护肩";
            case 3:
                return "项链";
            case 4:
                return "胸甲";
            case 5:
                return "腰带";
            case 6:
                return "裤子";
            case 7:
                return "护腿";
            case 8:
                return "鞋子";
            case 9:
                return "手套";
            case 10:
                return "戒指";
            case 11:
                return "主武器";
            case 12:
                return "副武器";
            default:
                return "未知";
        }
    }


    private static int getType(String type) {
        if ("头盔".equals(type)) {
            return 1;
        } else if ("护肩".equals(type)) {
            return 2;
        } else if ("项链".equals(type)) {
            return 3;
        } else if ("胸甲".equals(type)) {
            return 4;
        } else if ("腰带".equals(type)) {
            return 5;
        } else if ("裤子".equals(type)) {
            return 6;
        } else if ("护腿".equals(type)) {
            return 7;
        } else if ("鞋子".equals(type)) {
            return 8;
        } else if ("手套".equals(type)) {
            return 9;
        } else if ("戒指".equals(type)) {
            return 10;
        } else if ("主武器".equals(type)) {
            return 11;
        } else if ("副武器".equals(type)) {
            return 12;
        } else {
            return 0;
        }
    }


    public static String getColor(String rank) {
        String color = "";
        if ("普通物品".equals(rank)) {
            color = "";
        } else if ("魔法物品".equals(rank)) {
            color = "|cff3366ff";
        } else if ("稀有物品".equals(rank)) {
            color = "|cffff00ff";
        } else if ("传奇物品".equals(rank)) {
            color = "|cffff9900";
        }
        return color;
    }

    public static int getRank(String rank) {
        int r = 0;
        if ("普通物品".equals(rank)) {
            r = 1;
        } else if ("魔法物品".equals(rank)) {
            r = 2;
        } else if ("稀有物品".equals(rank)) {
            r = 3;
        } else if ("传奇物品".equals(rank)) {
            r = 4;
        }
        return r;
    }


    /**
     * call YDLocal1Set(itemcode, "ItemID", YDWES2ItemId("此处修改物品ID"))
     * call YDUserDataSet(itemcode, YDLocal1Get(itemcode, "ItemID"), "name", string, "物品名字")
     */
    public static String code(List<Item> datas) {
        StringBuilder allcode = new StringBuilder();
        for (Item i : datas) {
            StringBuffer itemcode = new StringBuffer(i.getName() + "(" + i.getType() + ")").append("\n");
            itemcode.append("call YDLocal1Set(itemcode, \"mItemID\", YDWES2ItemId(\"此处修改物品ID\"))\n");
            itemcode.append("call YDUserDataSet(itemcode, YDLocal1Get(itemcode, \"mItemID\"), \"name\", string,\"").append(getColor(i.getRank())).append(i.getName()).append("\")").append("\n");
            itemcode.append("call YDUserDataSet(itemcode, YDLocal1Get(itemcode, \"mItemID\"), \"describe\", string,\"|cffffcc00E").append(i.getDescribe()).append("\")").append("\n");
            itemcode.append("call YDUserDataSet(itemcode, YDLocal1Get(itemcode, \"mItemID\"), \"type\", integer,").append(getType(i.getType())).append(")").append("\n");
            itemcode.append("call YDUserDataSet(itemcode, YDLocal1Get(itemcode, \"mItemID\"), \"rank\", integer,").append(getRank(i.getRank())).append(")").append("\n");
            itemcode.append("call YDUserDataSet(itemcode, YDLocal1Get(itemcode, \"mItemID\"), \"lv\", integer,").append(100).append(")").append("\n");
            allcode.append(itemcode).append("\n\n\n");
        }

        return allcode.toString();
    }

    //创建物品数据，传入装备ID,可破坏物ID，装备名字，装备介绍，装备类型，装备品质，装备等级
    public static String code(Item i) {

        StringBuffer itemcode = new StringBuffer("//" + i.getName() + "(" + i.getType() + ")").append("\n");
        itemcode.append("call CreateItemDatas(\"装备ID\",\"图标ID\",")
                .append(getColor(i.getRank())).append(i.getName()).append("\"").append(",")
                .append("\"|cffffcc00E").append(i.getDescribe()).append("\"").append(",")
                .append(getType(i.getType())).append(",")
                .append(getRank(i.getRank())).append(",")
                .append(100)
                .append(")").append("\n\n");

        itemcode.append("");

        return itemcode.toString();


    }


}


























