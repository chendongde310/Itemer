package cn.com.cdgame.itemer;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2018/1/16 - 下午3:01
 * 注释：
 */
public class utils {

    /**
     *
     * @param code
     */
    private String getType(int code) {
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
}
