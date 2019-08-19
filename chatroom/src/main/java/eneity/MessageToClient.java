package eneity;


import java.util.Map;

/**
 * @Description:后端发送个前端的信息实体
 * @Author:Anan
 * @Date:2019/8/19
 */
public class MessageToClient {
    private String content;//聊天内容
    private Map<String,String> names;
    public void setContent(String userName,String content) {
        this.content = userName+":"+content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }
}
