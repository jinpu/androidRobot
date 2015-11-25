package bean;

import java.util.Date;

/**
 * Created by jinpu on 10/22/15.
 */
public class chatmessage {
    private String name;
    private String msg;
    private Type type;
    private Date date;

    public enum Type{
        INCOMING,OUTCOMING
    }

    public chatmessage()
    {

    }
    public chatmessage(String msg,Type type,Date data)
    {
        super();
        this.msg = msg;
        this.type = type;
        this.date = data;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setMsg(String Msg)
    {
        this.msg = Msg;
    }
    public String getMsg(){
        return msg;
    }
    public void setType(Type type)
    {
        this.type= type;
    }
    public Type getType(){
        return type;
    }
    public void setDate(Date date)
    {
        this.date= date;
    }
    public Date getDate(){
        return date;
    }
}
