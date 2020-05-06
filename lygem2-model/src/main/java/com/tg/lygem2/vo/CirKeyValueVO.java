package com.tg.lygem2.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CirKeyValueVO<T> {
	private String name;
	private T value;
}
