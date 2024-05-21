//package org.example.exception;
//
//import org.example.util.Result;
//import org.springframework.web.bind.annotation.*;
//
//@RestControllerAdvice
//public class GlobalException {
//    @ExceptionHandler(Exception.class)  //捕获所有的异常
//    public Result Ex(Exception e){
//        e.printStackTrace();    //输出异常的堆栈信息
//        return Result.error("对不起，操作失败，请重新操作！");
//    }
//}
