import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Categorias {
    private JPanel panelCategoria;
    private JTextArea textAreaCategorias;
    private JTextField textFieldNome;
    private JTextField textFieldID;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton saveButton;


    public Categorias() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldNome.getText();

                try{
                    Connection conn = Conexão.createConnection();
                    String sql = "insert into categoria(Nome)values(?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!!");

                    textFieldNome.setText("");
                    textFieldNome.requestFocus();

                    String sql2 = "select IDCategoria,Nome from categoria";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ResultSet rs2=ps2.executeQuery();
                    textAreaCategorias.setText("");
                    while(rs2.next()) {
                        String id2 = rs2.getString(1);
                        String name2 = rs2.getString(2);
                        textAreaCategorias.append("ID Categoria: " + id2 + ", Categoria: " + name2 + "\n");
                    }
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=textFieldID.getText();

                try{
                    Connection conn = Conexão.createConnection();
                    String sql = "delete from categoria where IDCategoria=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,id);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Delete!!");

                    textFieldNome.requestFocus();
                    textFieldID.setText("");
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = textFieldID.getText();
                    Connection conn = Conexão.createConnection();
                    String sql = "select Nome from categoria where IDCategoria = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,id);
                    ResultSet rs=ps.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        textFieldNome.setText(name);
                    }
                    else
                    {
                        textFieldNome.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldNome.getText();
                String id = textFieldID.getText();

                try{
                    Connection conn = Conexão.createConnection();
                    String sql = "update categoria set Nome = ? where IDCategoria = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.setString(2,id);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Update!!");

                    textFieldNome.setText("");
                    textFieldNome.requestFocus();
                    textFieldID.setText("");
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new Categorias().panelCategoria);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new Categorias().panelCategoria);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
