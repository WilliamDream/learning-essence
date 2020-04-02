package com.william.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormatController {

    @Autowired
    private FormatTemplate formatTemplate;

    @GetMapping("/format")
    public String format(){
        Student student = new Student();
        student.setStuId(123);
        student.setName("William");
        return formatTemplate.doFormat(student);
    }


}
