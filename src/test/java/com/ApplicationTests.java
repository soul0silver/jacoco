package com;

import com.example.demo.*;
import com.example.demo.model.Emp;
import com.example.demo.model.EmpDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {
    @MockBean
    EnpRepo enpRepo;
    @Mock
    EntityManager entityManager;
    @InjectMocks
    EmpoRepo2 repo2;
    Emp emp;
    List<EmpDto> resultList = new ArrayList<>();


    @Test
    @Rollback
    public void testAdd() {

        Emp emp = new Emp();
        Emp savedEmp = repo2.add(emp);
        Assert.assertNotNull(savedEmp);
        verify(entityManager, times(1)).persist(emp);
        verify(entityManager, times(1)).flush();
    }

    @Test
    public void testGet() {
        // Mock TypedQuery
        TypedQuery<EmpDto> query = mock(TypedQuery.class);
        when(entityManager.createQuery("select v.id as id,u.fullname as fullname from Emp as v,User u where u.emp.id=v.id", EmpDto.class)).thenReturn(query);

        EmpDto empDto1 = new EmpDto();
        empDto1.setId(1L);
        empDto1.setFullname("John Doe");
        resultList.add(empDto1);
        EmpDto empDto2 = new EmpDto();
        empDto2.setId(2L);
        empDto2.setFullname("Jane Doe");
        resultList.add(empDto2);
        when(query.getResultList()).thenReturn(resultList);

        // Mock count query
        Query count = mock(Query.class);
        when(entityManager.createQuery("select count(v.id) from Emp as v,User u where u.emp.id=v.id")).thenReturn(count);
        when(count.getSingleResult()).thenReturn(resultList.size());

        // Call your service method
        Page<EmpDto> page = repo2.get(1L);

        assertEquals(2L,page.getTotalElements());
        // Verify the result
        assertEquals(2, page.getContent().size());
        Assert.assertEquals(Long.valueOf(1L), page.getContent().get(0).getId());
        assertEquals("John Doe", page.getContent().get(0).getFullname());
        assertEquals(Long.valueOf(2L), page.getContent().get(1).getId());
        assertEquals("Jane Doe", page.getContent().get(1).getFullname());
        assertEquals(2L, page.getTotalElements());
        assertEquals(0, page.getNumber());
        assertEquals(5, page.getSize());
    }
}
