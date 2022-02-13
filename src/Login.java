import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private JButton enterButton;
    private JTextField textFieldUserName;
    private JPanel panelLogin;
    private JPasswordField passwordField1;

    public Login() {
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user= textFieldUserName.getText();
                String pass = passwordField1.getText();

                try {
                    Connection conn = Conexão.createConnection();
                    String sql = "SELECT Nome,Telefone,UserName,PassWord FROM empregados";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet sr = ps.executeQuery();
                    boolean fg = false;

                    while (sr.next()) {
                        String nome = sr.getString(1);
                        String userc = sr.getString("UserName");
                        String passc = sr.getString("PassWord");
                        String telefonec = sr.getString("Telefone");

                        if (user.equals(userc) && pass.equals(passc)) {
                            new LojaMenu().setVisible(true);
                            fg = true;
                        }
                    }
                    if (!fg) {
                        JOptionPane.showMessageDialog(null, "Login errado!" +
                                " username/password incorretos!");
                    }


                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new Login().panelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
