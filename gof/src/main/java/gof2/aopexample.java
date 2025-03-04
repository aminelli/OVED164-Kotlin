package com.etlforma.examples.aop

// Importazioni necessarie per AspectJ
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;

// Classe di servizio principale
class BankService {
    public void depositMoney(String accountId, double amount) {
        System.out.println("Depositing " + amount + " to account " + accountId);
    }

    public double withdrawMoney(String accountId, double amount) {
        System.out.println("Withdrawing " + amount + " from account " + accountId);
        return amount;
    }
}

// Aspetto per la gestione della sicurezza
@Aspect
class SecurityAspect {
    // Pointcut che cattura tutti i metodi nel BankService
    @Pointcut("execution(* BankService.*(..))")
    public void bankServiceMethods() {}

    // Advice che viene eseguito prima dei metodi del BankService
    @Before("bankServiceMethods()")
    public void checkSecurity(JoinPoint joinPoint) {
        System.out.println("[SECURITY] Checking authorization for method: "
                + joinPoint.getSignature().getName());
    }
}

// Aspetto per la registrazione dei log
@Aspect
class LoggingAspect {
    // Pointcut specifico per il metodo depositMoney
    @Pointcut("execution(* BankService.depositMoney(..)) && args(accountId, amount)")
    public void depositMethod(String accountId, double amount) {}

    // Advice dopo l'esecuzione del metodo di deposito
    @AfterReturning("depositMethod(accountId, amount)")
    public void logDeposit(String accountId, double amount) {
        System.out.println("[LOGGING] Deposit logged: " + amount +
                " to account " + accountId);
    }

    // Advice che si applica a tutti i metodi del BankService dopo l'esecuzione
    @After("execution(* BankService.*(..))")
    public void logMethodCompletion(JoinPoint joinPoint) {
        System.out.println("[LOGGING] Method " +
                joinPoint.getSignature().getName() + " completed");
    }
}

// Classe principale per dimostrare l'AOP
public class AOPExample {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        // Eseguiamo alcune operazioni bancarie
        bankService.depositMoney("ACC001", 1000.00);
        bankService.withdrawMoney("ACC001", 500.00);
    }
}