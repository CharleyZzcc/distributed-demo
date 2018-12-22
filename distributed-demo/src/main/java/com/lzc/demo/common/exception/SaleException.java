
 /**
 * FileName:     SellException.java
 * Createdate:   2018-12-19 23:53:41   
 */

package com.lzc.demo.common.exception;

import lombok.Getter;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 23:53:41   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@Getter
public class SaleException extends RuntimeException {

    private Integer code;

    public SaleException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
