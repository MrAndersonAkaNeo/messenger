package com.ntu.messenger.api.criteria;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class MessageCriteria {

    @Min(1)
    @Max(200)
    private Integer size = 50;

    @Min(0)
    private Integer page = 0;
}
