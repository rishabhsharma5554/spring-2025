package com.rishabhtech.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 *  Aspect : It is a Module encapsulating Advices & JointPoints
 *  Advice : It is a Code to be executed at some JoinPoint
 *  JoinPoint : A point in your program where Spring AOP can insert some additional code. i.e advice code
 *  Pointcut : Expression to group one or more Join Points
 *  Weaving : process of linking aspects with the target objects/business logic
 *              Spring AOP be default support Runtime Weaving
 *
 *
 * order of execution of aspects
 * @Around (before part)-> @Before -> @AfterReturning -> @After ->@Advice (after part) (Happy Flow)
 * @Around (before part)-> @Before -> @AfterTThrowing -> @After (When your method throws exception)
 */
@Component // to be scaneed and stored in spring context
@Order(2)
@Aspect // mark this class as an aspect
public class LoggingAspect {

    /*
     *
     * @Before advice is executed before the target method invocation.
     */
    @Before(value = "execution(* com.rishabhtech.aop.controller.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        System.out.println("@Before method: " + joinPoint.getSignature());
    }

    /*
     * @After advice is executed after the method, regardless of its outcome (success or failure).
     */
    @After(value = "execution(* com.rishabhtech.aop.controller.*.*(..))")
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        System.out.println("\n@After method: " + joinPoint.getSignature());
    }

    /*
    To use @Around, it accepts a parameter of type ProceddingJoinPoint to invoke actual method
    and throws Throwable.

    when you need complete controller over method execution you can use @Around

    It always executes the code before and after the method, regardless of whether
    the method completes successfully or throws an exception.
     */
    @Around(value = "execution(* com.rishabhtech.aop.controller.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        Long startTime = System.currentTimeMillis();
        System.out.println("\n@Around (Before) executing target method");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("@Around (After) executing target method");
        Long endTime = System.currentTimeMillis();
        Long totalTime = endTime-startTime;
        System.out.println("Execution time of "+proceedingJoinPoint.getSignature().getName()+" : "+totalTime+" ms");
        return result;
    }

    /*
     * @AfterThrowing advice is executed if the method throws an exception.
     * Throwable ex and the throwing = "ex" both must be same
     *
     */
    @AfterThrowing(pointcut = "execution(* com.rishabhtech.aop.controller.*.*(..))", throwing = "ex")
    public void logAfterThrowingMethodExecution(JoinPoint joinPoint, Throwable ex) {
        System.out.println("\n@AfterThrowing Exception in method: " + joinPoint.getSignature());
        System.out.println("Exception: " + ex.getMessage());
    }

    /*
     * @AfterReturning advice is executed after the method returns successfully.
     */
    @AfterReturning(value = "execution(* com.rishabhtech.aop.controller.*.*(..))", returning = "result")
    public void logAfterReturningMethodExecution(JoinPoint joinPoint, Object result) {
        System.out.println("\n@AfterReturnig from method: " + joinPoint.getSignature());
        System.out.println("Method returned value: " + result);
    }
}