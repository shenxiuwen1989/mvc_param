package com.sxw.entry;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class RequestStudent implements Serializable {

    private String code;
    private String msg;
    private List<Student> data;
}
