package com.interest.myapplication.controller;

/**
 * Created by Android on 2016/3/22.
 */
public class MainController {
    public static final int LOGIN = 0;
    public static final int SEND = 1;
    public static final int SEARCHBOOK = 2;

    public static void handlerMessage(int index,int type, Object object){
       switch (type){
           case LOGIN:
               break;
           case SEND:
//               ChatBiz.send(index,(String)object);
               break;
       }
    }
}
