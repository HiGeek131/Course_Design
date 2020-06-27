import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static final String db_url = "jdbc:mysql://higeekstudio.cn:3306/Course_Design?useSSL=false";
    public static final String db_user = "gary";
    public static final String db_password = "827289014Aa131";

    public static SqlDB sqlDB;

    public static void main(String[] args) {
        //小学四四则运算能力检测系统
        InterfaceManagement interfaceManagement = new InterfaceManagement("小学生四则运算能力检测系统");
        interfaceManagement.interfaceLogin();

        sqlDB = new SqlDB(db_url, db_user, db_password);
        if (sqlDB.sqlConnect()) {
            System.out.println("连接成功");
        }
    }
}
