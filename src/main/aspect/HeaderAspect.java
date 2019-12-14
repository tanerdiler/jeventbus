//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//
////@Aspect
//public class HeaderAspect {
//
//    @Pointcut("execution(void jeventbus.core.EventPathListenerNode.execute(..))")
//    public void defineEntryPoint() {
//    }
//
//    @Around("defineEntryPoint()")
//    public void aaa(ProceedingJoinPoint joinPoint) throws Throwable{
//        System.out.println(joinPoint.getTarget().getClass().getName());
//    }
//
//}
