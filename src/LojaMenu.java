import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class LojaMenu {
    private JPanel panelLoja;
    private JTextField textFieldName;
    private JTextField textFieldPrice;
    private JTextField textFieldQty;
    private JLabel labelFoto;
    private JButton salvarButton;
    private JButton browseButton;
    private JTextField textFieldCategory;
    private JTextField textFieldID;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JTextField textFieldCategoria;
    private JButton FINDButton;
    private JTextArea textAreaCategorias;
    private JButton irParaCategoriasButton;
    private JButton irParaListasProdutosButton;

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
    public LojaMenu() {
        Connection();
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String price = textFieldPrice.getText();
                String qty = textFieldQty.getText();
                String cat = textFieldCategory.getText();


                try{
                    Connection conn = Conexão.createConnection();
                    String sql = "insert into produto(Nome,Preco,Quantidade,IDCategoria,Foto)values(?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.setString(2,price);
                    ps.setString(3,qty);
                    ps.setString(4,cat);
                    ps.setBytes(5,userImage);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!!");

                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQty.setText("");
                    textFieldCategory.setText("");
                    labelFoto.setIcon(null);
                    textFieldName.requestFocus();
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
                    String sql = "delete from produto where IDProduto=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,id);

                    ps.executeUpdate();


                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQty.setText("");
                    textFieldName.requestFocus();
                    labelFoto.setIcon(null);
                    textFieldID.setText("");
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String price = textFieldPrice.getText();
                String qty = textFieldQty.getText();
                String id = textFieldID.getText();
                String cat = textFieldCategory.getText();
                try{
                    Connection conn = Conexão.createConnection();
                    String sql = "update produto set Nome = ?, Preco = ?, " +
                            "Quantidade = ?, IDCategoria =?,Foto=? where IDProduto = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.setString(2,price);
                    ps.setString(3,qty);
                    ps.setString(4,cat);
                    ps.setBytes(5,userImage);
                    ps.setString(6,id);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Update!!");
                    labelFoto.setIcon(null);
                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQty.setText("");
                    textFieldName.requestFocus();
                    textFieldID.setText("");
                    textFieldCategory.setText("");
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
                    String sql = "select Nome,Preco,Quantidade,IDCategoria,Foto from produto where IDProduto = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,id);
                    ResultSet rs=ps.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);
                        String cat = rs.getString(4);
                        Blob blob = rs.getBlob("Foto");
                        textFieldName.setText(name);
                        textFieldPrice.setText(price);
                        textFieldQty.setText(qty);
                        textFieldCategory.setText(cat);
                        byte[] imageBytes=blob.getBytes(1,(int)blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                        labelFoto.setIcon(imgIcon);
                    }
                    else
                    {
                        textFieldName.setText("");
                        textFieldPrice.setText("");
                        textFieldQty.setText("");
                        textFieldCategory.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imagChoosed = new JFileChooser();
                imagChoosed.showOpenDialog(null);
                File file = imagChoosed.getSelectedFile();

                path=file.getAbsolutePath();
                BufferedImage img;
                try{
                    img= ImageIO.read(imagChoosed.getSelectedFile());
                    ImageIcon imageIcon = new ImageIcon
                            (new ImageIcon(img).getImage().getScaledInstance
                                    (250,250, Image.SCALE_DEFAULT));
                    labelFoto.setIcon(imageIcon);

                    //guardar imagem na variavel byte[] "userImage" para
                    //ser depois guardada na base de dados

                    File image=new File(path);
                    FileInputStream fs=new FileInputStream(image);
                    ByteArrayOutputStream bos= new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nBytesRead=0;
                    while((nBytesRead=fs.read(buff))!=-1)
                        bos.write(buff,0,nBytesRead);
                    userImage = bos.toByteArray();

                }catch(IOException el){
                    el.printStackTrace();
                }
            }
        });
        FINDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = textFieldCategoria.getText();
                try{
                    Connection conn = Conexão.createConnection();
                    String sql = "select Nome from produto where IDCategoria = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,ID);
                    ResultSet rs=ps.executeQuery();
                    textAreaCategorias.setText("");
                    while(rs.next()) {
                        String name = rs.getString(1);
                        textAreaCategorias.append(name + "\n");
                    }
                }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
        });
        irParaCategoriasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Categorias().setVisible(true);
            }
        });
        irParaListasProdutosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Produtos().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new LojaMenu().panelLoja);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new LojaMenu().panelLoja);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
