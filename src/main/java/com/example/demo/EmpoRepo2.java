package com.example.demo;

import com.example.demo.model.Emp;
import com.example.demo.model.EmpDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class EmpoRepo2 {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Emp add(Emp emp) {
        entityManager.persist(emp);
        entityManager.flush();
        return emp;
    }

    public Page<EmpDto> get(Long id){
        ObjectMapper mapper = new ObjectMapper();
        TypedQuery<EmpDto> query = entityManager.createQuery("select v.id as id,u.fullname as fullname from Emp as v,User u where u.emp.id=v.id", EmpDto.class);
        if (query != null) {
            query.setFirstResult(0);
            query.setMaxResults(5);

            List<EmpDto> list = query.getResultList();
            Query count = entityManager.createQuery("select count(v.id) from Emp as v,User u where u.emp.id=v.id ");
            Long c =count!=null? (Long) count.getSingleResult():0L;
            return new PageImpl<EmpDto>(list, PageRequest.of(0, 5), c);
        }
        return null;
    }
}
