package com.atguigu.common.constant;

public class ProductConstant {
    public enum AttrEnum {
        //属性类型[0-销售属性，1-基本属性]
        ATTR_TYPE_SALE(0, "销售属性"),
        ATTR_TYPE_BASE(1, "基本属性");

        private int code;
        private String value;

        AttrEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum StatusEnum {

        NEW_SPU(9, "新建"), SPU_UP(1, "商品上架"), SPU_DOWN(2, "商品下架");
        private int code;
        private String value;

        StatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
