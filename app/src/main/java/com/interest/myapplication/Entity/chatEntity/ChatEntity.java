package com.interest.myapplication.Entity.chatEntity;

/**
 * Created by Android on 2016/3/22.
 * 机器人交谈的类
 */
public class ChatEntity {
    private String content;

    private String moduleId;

    private String nodeId;

    private int similarity;

    private int type;

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setModuleId(String moduleId){
        this.moduleId = moduleId;
    }
    public String getModuleId(){
        return this.moduleId;
    }
    public void setNodeId(String nodeId){
        this.nodeId = nodeId;
    }
    public String getNodeId(){
        return this.nodeId;
    }
    public void setSimilarity(int similarity){
        this.similarity = similarity;
    }
    public int getSimilarity(){
        return this.similarity;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
}
