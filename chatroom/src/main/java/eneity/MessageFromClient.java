package eneity;

/**
 * @Description:前端发送给后端的信息实体类
 * @Author:Anan
 * @Date:2019/8/19
 */
public class MessageFromClient {
    private String msg;
    //1:聊天 2：私聊
    private String type;
    //私聊对象SessionID
    private String to;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
