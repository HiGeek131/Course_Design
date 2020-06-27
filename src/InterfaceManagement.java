import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterfaceManagement extends JFrame {
    private static JPanel jPanel;
    private static Font font;
    private static int numberOfQuestions = 0;
    private static int numberOfQuestionsCorrect = 0;
    private static String userName = null;


    InterfaceManagement(String title) {
        super(title);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setResizable(false);
        ImageIcon imageIcon = new ImageIcon("技术宅之家.png");
        this.setIconImage(imageIcon.getImage());
        this.setVisible(true);
        font = new Font("宋体", Font.PLAIN, 20);
        jPanel = new JPanel();
        jPanel.setLayout(null);
        this.add(jPanel);
    }

    void interfaceLogin() {
        jPanel.removeAll();

        JLabel nameLabel = new JLabel("用户名：");
        nameLabel.setFont(font);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setBounds(100, 50, 100, 30);
        jPanel.add(nameLabel);

        JTextField nameTextField =  new  JTextField();
        nameTextField.setFont(font);
        nameTextField.setBounds(200, 50, 200, 30);
        jPanel.add(nameTextField);


        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setFont(font);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setBounds(100, 150, 100, 30);
        jPanel.add(passwordLabel);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(font);
        passwordTextField.setBounds(200, 150, 200, 30);
        jPanel.add(passwordTextField);

        JButton loginButton = new JButton("登录");
        loginButton.setFont(font);
        loginButton.setBounds(100, 300, 100, 30);
        jPanel.add(loginButton);

        JButton signUpButton = new JButton("注册");
        signUpButton.setFont(font);
        signUpButton.setBounds(300, 300, 100, 30);
        jPanel.add(signUpButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String sqlCom = String.format("select password from user where name='%s';", name);
//                String sqlCom = "select * from user";
                String password = null;
                try {
                    ResultSet resultSet = Main.sqlDB.sqlExecuteQueue(sqlCom);
                    if (resultSet.next()) {
//                        System.out.println(resultSet.getString("password"));
                        password = resultSet.getString("password");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (password == null) {
                    JOptionPane.showMessageDialog(null, "无此用户！");
                }
                else if (password.equals(new String(passwordTextField.getPassword()))) {
                    userName = name;
                    interfaceTest();
                }
                else {
                    JOptionPane.showMessageDialog(null, "密码错误！");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                interfaceSignUp();
            }
        });

        this.revalidate();
        this.repaint();
    }

    void interfaceSignUp() {
        jPanel.removeAll();

        JLabel nameLabel = new JLabel("用户名：");
        nameLabel.setFont(font);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setBounds(100, 50, 100, 30);
        jPanel.add(nameLabel);

        JTextField nameTextField =  new  JTextField();
        nameTextField.setFont(font);
        nameTextField.setBounds(200, 50, 200, 30);
        jPanel.add(nameTextField);

        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setFont(font);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setBounds(100, 150, 100, 30);
        jPanel.add(passwordLabel);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(font);
        passwordTextField.setBounds(200, 150, 200, 30);
        jPanel.add(passwordTextField);

        JLabel passwordLabel2 = new JLabel("确认密码：");
        passwordLabel2.setFont(font);
        passwordLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel2.setBounds(100, 250, 100, 30);
        jPanel.add(passwordLabel2);

        JPasswordField passwordTextField2 = new JPasswordField();
        passwordTextField2.setFont(font);
        passwordTextField2.setBounds(200, 250, 200, 30);
        jPanel.add(passwordTextField2);

        JButton confirmButton = new JButton("确认");
        confirmButton.setFont(font);
        confirmButton.setBounds(100, 350, 100, 30);
        jPanel.add(confirmButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.setFont(font);
        cancelButton.setBounds(300, 350, 100, 30);
        jPanel.add(cancelButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String pass0 = new String(passwordTextField.getPassword());
                String pass1 = new String(passwordTextField2.getPassword());

                String sqlCom = String.format("select name from user where name='%s';", name);
                try {
                    ResultSet resultSet = Main.sqlDB.sqlExecuteQueue(sqlCom);
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "用户已存在！");
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (pass0.equals(pass1)) {
                    String sqlCom1 = String.format("insert user (name, password) values ('%s', '%s');", name, pass0);
                    if (Main.sqlDB.sqlExecuteUpdate(sqlCom1) != -1) {
                        JOptionPane.showMessageDialog(null, "注册成功！");
                        interfaceLogin();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "服务器出问题啦，请稍后再试。");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "两次输入密码不同！请检查。");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                interfaceLogin();
            }
        });

        this.revalidate();
        this.repaint();
    }

    void interfaceTest() {
        jPanel.removeAll();

        JLabel questionsLabel = new JLabel();
        questionsLabel.setFont(font);
        questionsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        questionsLabel.setBounds(100, 100, 100, 30);
        jPanel.add(questionsLabel);

        JButton answer0 = new JButton();
        answer0.setFont(font);
        answer0.setBounds(100, 200, 100, 30);
        jPanel.add(answer0);

        JButton answer1 = new JButton();
        answer1.setFont(font);
        answer1.setBounds(300, 200, 100, 30);
        jPanel.add(answer1);

        JButton answer2 = new JButton();
        answer2.setFont(font);
        answer2.setBounds(100, 250, 100, 30);
        jPanel.add(answer2);

        JButton answer3 = new JButton();
        answer3.setFont(font);
        answer3.setBounds(300, 250, 100, 30);
        jPanel.add(answer3);

//        JButton exitButton = new JButton("退出");
//        exitButton.setFont(font);
//        exitButton.setBounds(200, 400, 100, 30);
//        jPanel.add(exitButton);
        int num0, num1, answer = 0;
        num0 =  (int) (Math.random() * 99) + 1;
        num1 =  (int) (Math.random() * 99) + 1;
        int operator = (int) (Math.random() * 4);
        String operatorString = "";
        switch (operator) {
            case 0:
                answer = num0 + num1;
                operatorString = "+";
                break;
            case 1:
                answer = num0 - num1;
                operatorString = "-";
                break;
            case 2:
                answer = num0 * num1;
                operatorString = "*";
                break;
            case 3:
                answer = num0 / num1;
                operatorString = "/";
                break;
            default:
                break;
        }

        questionsLabel.setText(String.format("%d%s%d=?", num0, operatorString, num1));

        answer0.setText(String.valueOf((int) (Math.random() * 999) + 1));
        answer1.setText(String.valueOf((int) (Math.random() * 9999) + 1));
        answer2.setText(String.valueOf((int) (Math.random() * 99) + 1));
        answer3.setText(String.valueOf((int) (Math.random() * 9999) + 1));

        int setAnswerButton = (int) (Math.random() * 4);

        switch (setAnswerButton) {
            case 0:
                answer0.setText(String.valueOf(answer));
                break;
            case 1:
                answer1.setText(String.valueOf(answer));
                break;
            case 2:
                answer2.setText(String.valueOf(answer));
                break;
            case 3:
                answer3.setText(String.valueOf(answer));
                break;
            default:
                break;
        }

        int finalAnswer = answer;
        answer0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                numberOfQuestions++;
                if (answer0.getText().equals(String.valueOf(finalAnswer))) {
                    numberOfQuestionsCorrect++;
                }
                interfaceTest();
                if (numberOfQuestions == 20) {
                    settlement();
                }
            }
        });

        answer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                numberOfQuestions++;
                if (answer1.getText().equals(String.valueOf(finalAnswer))) {
                    numberOfQuestionsCorrect++;
                }
                interfaceTest();
                if (numberOfQuestions == 20) {
                    settlement();
                }
            }
        });

        answer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                numberOfQuestions++;
                if (answer2.getText().equals(String.valueOf(finalAnswer))) {
                    numberOfQuestionsCorrect++;
                }
                interfaceTest();
                if (numberOfQuestions == 20) {
                    settlement();
                }
            }
        });

        answer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                numberOfQuestions++;
                if (answer3.getText().equals(String.valueOf(finalAnswer))) {
                    numberOfQuestionsCorrect++;
                }
                interfaceTest();
                if (numberOfQuestions == 20) {
                    settlement();
                }
            }
        });

        this.revalidate();
        this.repaint();
    }

    void settlement() {
        jPanel.removeAll();

        int correct = numberOfQuestionsCorrect * 5;
        JLabel fractionLabel = new JLabel(String.format("你的分数是：%d分", correct));
        fractionLabel.setFont(font);
        fractionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fractionLabel.setBounds(100, 100, 200, 30);
        jPanel.add(fractionLabel);

        String sqlCom = String.format("insert fraction (name, fraction) values ('%s', %d);", userName, correct);
        if (Main.sqlDB.sqlExecuteUpdate(sqlCom) == -1) {
            JOptionPane.showMessageDialog(null, "服务器出问题啦，成绩上传失败。");
        }

        JButton confirmButton = new JButton("确认");
        confirmButton.setFont(font);
        confirmButton.setBounds(200, 300, 100, 30);
        jPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                interfaceTest();
            }
        });

        this.revalidate();
        this.repaint();
    }
}


