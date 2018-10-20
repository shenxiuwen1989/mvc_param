package com.sxw.entry;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Student implements Serializable {
   private String name;
   private int age;

   public Student(String name,int age){
       this.name = name ;
       this.age = age;
   }

}
