package com.example.vehicle_transport_calculator.web.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
  @Pointcut("@annotation(WarnIfExecutionExceeds)")
  void onWarnIfExecutionTimeExceeds(){}
}
