package com.thinkgem.jeesite.modules.signature.web;

public class Client {

public static void main(String []args){
//    new WriteThread("d:/test1.txt", "d:/test.txt", "write_thread-1").start();
//    new WriteThread("d:/test2.txt", "d:/test.txt", "write_thread-2").start();  

    new ReadThread("d:/test.txt", "read_thread-1").start();
    new ReadThread("d:/test.txt", "read_thread-2").start();

}
}