package cn.com.partical.development.system.developmentservice.util.filed;

/**
 * @author zhangWang
 * @date 2020/11/20 15:24
 */
public enum ActiveFlagEnum {
    /**
     * 默认正常
     */
    DEFAULT(Integer.valueOf(0).shortValue(), "正常"),
    /**
     * 数据已删除
     */
    DELETE(Integer.valueOf(1).shortValue(), "已删除");

    private Short value;
    private String desc;


    ActiveFlagEnum() {
    }

    ActiveFlagEnum(Short value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Short getValue() {
        return this.value;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return this.value + "";
    }
}
