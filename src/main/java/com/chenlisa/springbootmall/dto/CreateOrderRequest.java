package com.chenlisa.springbootmall.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateOrderRequest {

    /***
     * annotation
     * NotNull  -> 不能為 null
     * NotBlank -> 不能為 null、且不能為空白字串、用於驗證 String 類型的參數上
     * NotEmpty -> 不能為 null、且 size 必須 > 0，用於驗證集合類型（List、Set、Map）的參數上
     * Min      -> 值必須 >= value，用於數字類型的參數上
     * Max      -> 值必須 <= value，用於數字類型的參數上
     * */

    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
