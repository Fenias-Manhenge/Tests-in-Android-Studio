package com.example.tests

import java.util.Arrays

fun main(){
    println("Seu nome...")
    val any = readlnOrNull()

    if(any.equals(any?.reversed(), true))
        println("Palindrome")
    else
        println("It's not Palindrome")
}