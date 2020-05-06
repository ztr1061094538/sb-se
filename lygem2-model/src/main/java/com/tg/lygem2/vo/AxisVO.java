package com.tg.lygem2.vo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AxisVO {
    private String name;
    @Getter(onMethod= @_({@JsonGetter("xAxis")}))
    @Setter(onMethod= @_({@JsonSetter("xAxis")}))
    private String xAxis;
}
