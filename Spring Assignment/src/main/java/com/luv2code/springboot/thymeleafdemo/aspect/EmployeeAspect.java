package com.luv2code.springboot.thymeleafdemo.aspect;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class EmployeeAspect {
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.find*(..))")
    public void forEmployeeList() {}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.deleteById(..))")
    public void forDeleteEmployee() {}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.save(..))")
    public void savingEmployee() {

    }


    @Before("forEmployeeList()")
    public void beforeFinding(){
        System.out.println("\n\nEmployess lists\n\n");
    }

    @AfterReturning(
            pointcut="execution(* com.luv2code.springboot.thymeleafdemo.service.*.find*(..))",
            returning="result")
    public void afterReturningFindAccountsAdvice(
            JoinPoint theJoinPoint, List<Employee> result) {

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);
        System.out.println("\n=====>>> result is: " + result);
    }


    @Before("forDeleteEmployee()")
    public void beforeDelete(){
        System.out.println("\n\nDeleting Employee\n\n");
    }


    @Before("savingEmployee()")
    public void addingEmployee(){
        System.out.println("\n\nAdding Employee\n\n");
    }

    @AfterReturning(
            pointcut ="execution(* com.luv2code.springboot.thymeleafdemo.service.*.save(..))", returning="result")
    public void afterAddingEmployee(
            JoinPoint theJoinPoint, List<Employee> result) {

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);
        System.out.println("\n=====>>> result is: " + result);
    }



}
