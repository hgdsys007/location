package com.example.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GroupMessage implements Serializable {

    @JSONField(name = "MessageId")
    private int messageId;
    @JSONField(name = "GroupId")
    private int groupId;
    @JSONField(name = "FromId")
    private String fromId; //发起人id,groupId对应的adminId

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}
