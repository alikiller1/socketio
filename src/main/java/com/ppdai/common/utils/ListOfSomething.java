package com.ppdai.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2015  
 * Company:		拍拍贷
 * Author:		denghongbing
 * Version:		1.0  
 * Create at:	2015年9月7日 下午7:30:19  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class ListOfSomething<X> implements ParameterizedType {

	private Class<?> wrapped;

	public ListOfSomething(Class<X> wrapped) {
		this.wrapped = wrapped;
	}

	public Type[] getActualTypeArguments() {
		return new Type[] { wrapped };
	}

	public Type getRawType() {
		return List.class;
	}

	public Type getOwnerType() {
		return null;
	}

	
}