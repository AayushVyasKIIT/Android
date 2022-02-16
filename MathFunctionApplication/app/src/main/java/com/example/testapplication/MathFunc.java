package com.example.testapplication;

public class MathFunc {
    public int factorial(int n){

        if(n == 0){
            return 1;
        }

        return n * (factorial(n-1));
    }

    public int fib(int n){
        if(n == 0 || n == 1){
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }

}
