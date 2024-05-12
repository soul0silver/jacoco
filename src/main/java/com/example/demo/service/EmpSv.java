package com.example.demo.service;

import com.example.demo.EnpRepo;
import com.example.demo.model.Emp;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpSv {
    @Autowired
    EnpRepo enpRepo;
    public Emp save(){
        Emp e=new Emp();
        e.setTime(Timestamp.valueOf(LocalDateTime.now()));
        User u= new User();
        u.setEmp(e);
        u.setFullname("dsadasdad");
        List<User>list=new ArrayList<>();
        list.add(u);
        e.setUser(list);
        return enpRepo.save(e);
    }
}
