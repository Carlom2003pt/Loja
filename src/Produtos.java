import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Produtos {
    private JPanel panelProdutos;
    private JTextField textFieldID;
    private JTextField textFieldQty;
    private JTextField textFieldName;
    private JTextField textFieldCategory;
    private JTextField textFieldPrice;
    private JLabel labelFoto;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    private String path=null;
    private byte[] userImage;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;
    public void Connection()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdlojacarlos?useSSL=false","root","1234");
            st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select IDProduto,Nome,Preco,Quantidade,IDCategoria,Foto from produto");

        }catch(ClassNotFoundException el){
            el.printStackTrace();
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public Produtos() {
        Connection();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.first();
                    textFieldID.setText(String.valueOf(rs.getInt("IDProduto")));
                    textFieldName.setText(rs.getString("Nome"));
                    textFieldPrice.setText(String.valueOf(rs.getDouble("Preco")));
                    textFieldQty.setText(String.valueOf(rs.getInt("Quantidade")));
                    textFieldCategory.setText(String.valueOf(rs.getInt("IDCategoria")));
                    Blob blob = rs.getBlob("Foto");
                    byte[] imageBytes=blob.getBytes(1,(int)blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    labelFoto.setIcon(imgIcon);
                }catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!rs.isFirst()) {
                        rs.previous();
                        textFieldID.setText(String.valueOf(rs.getInt("IDProduto")));
                        textFieldName.setText(rs.getString("Nome"));
                        textFieldPrice.setText(String.valueOf(rs.getDouble("Preco")));
                        textFieldQty.setText(String.valueOf(rs.getInt("Quantidade")));
                        textFieldCategory.setText(String.valueOf(rs.getInt("IDCategoria")));
                        Blob blob = rs.getBlob("Foto");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelFoto.setIcon(imgIcon);
                    }
                }catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!rs.isLast()) {
                        rs.next();
                        textFieldID.setText(String.valueOf(rs.getInt("IDProduto")));
                        textFieldName.setText(rs.getString("Nome"));
                        textFieldPrice.setText(String.valueOf(rs.getDouble("Preco")));
                        textFieldQty.setText(String.valueOf(rs.getInt("Quantidade")));
                        textFieldCategory.setText(String.valueOf(rs.getInt("IDCategoria")));
                        Blob blob = rs.getBlob("Foto");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelFoto.setIcon(imgIcon);
                    }
                }catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.last();
                    textFieldID.setText(String.valueOf(rs.getInt("IDProduto")));
                    textFieldName.setText(rs.getString("Nome"));
                    textFieldPrice.setText(String.valueOf(rs.getDouble("Preco")));
                    textFieldQty.setText(String.valueOf(rs.getInt("Quantidade")));
                    textFieldCategory.setText(String.valueOf(rs.getInt("IDCategoria")));
                    Blob blob = rs.getBlob("Foto");
                    byte[] imageBytes=blob.getBytes(1,(int)blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    labelFoto.setIcon(imgIcon);
                }catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new Produtos().panelProdutos);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new Produtos().panelProdutos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
