import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Registradora extends JFrame{

    JComboBox <String> cmbRegistradora;
    JTextField txtRegistradora;
    JTextField txtvalor,txtCantidad;
    JTable tblRegistradora;
    DefaultTableModel dtm;

    int[] opciones = new int[]{50000,20000,10000,5000,2000,1000,500,200,100,50};
    String[] encabezados= new String[]{"Denominacion","Presentacion","Cantidad"};
    int[] regDenominacion = new int[1000];
    int[] regCantidad = new int[1000];
    JList<String> lstRegistros;
    int totalRegistros = -1;
    HashMap<Integer, String> Denominacion = new HashMap<>();

    public Registradora(){
        setSize(650,450);
        setTitle("Caja registradora");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

// profe en esta parte pedi ayuda porque no sabia como ponerla.
        Denominacion.put(20000, "Billete");
        Denominacion.put(10000, "Billete");
        Denominacion.put(2000, "Billete");
        Denominacion.put(1000, "Billete");
        Denominacion.put(500, "Moneda");
        Denominacion.put(200, "Moneda");
        Denominacion.put(50, "Moneda");


        JLabel lblDenominacion = new JLabel ("Denominacion");
        lblDenominacion.setBounds(100,10,100,25);
        getContentPane().add(lblDenominacion);

        String[] opciones = {"20000", "10000", "2000", "1000", "500", "200", "50"};
        cmbRegistradora = new JComboBox<>(opciones);
        cmbRegistradora.setBounds(230,10,100,25);
        getContentPane().add(cmbRegistradora);

       

        JButton btnActualizar= new JButton("Actualizar existencia");
        btnActualizar.setBounds(30,50,170,25);
        getContentPane().add(btnActualizar);

        txtRegistradora= new JTextField();
        txtRegistradora.setBounds(230,50,100,25);
        getContentPane().add(txtRegistradora);

        JLabel lblValor = new JLabel("Valor a Devolver");
        lblValor.setBounds(90,90,170,25);
        getContentPane().add(lblValor);

        txtvalor= new JTextField();
        txtvalor.setBounds(230,90,100,25);
        getContentPane().add(txtvalor);


        JButton btnDevolver= new JButton("Devolver");
        btnDevolver.setBounds(370,90,100,25);
        getContentPane().add(btnDevolver);

        tblRegistradora = new JTable();
        JScrollPane spRegistradora= new JScrollPane(tblRegistradora);
        spRegistradora.setBounds(10,230,550,150);
        getContentPane().add(spRegistradora);

        dtm= new DefaultTableModel(null,encabezados);
        tblRegistradora.setModel(dtm);

        // Eventos de la GUI(interfas grafica de usuario)
    
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });
       

        btnDevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                DevolverValor();
            }
        });

       
    }
    private void actualizar() {
        try {
            int denominacion = Integer.parseInt(cmbRegistradora.getSelectedItem().toString());
            int cantidad = Integer.parseInt(txtRegistradora.getText());
            totalRegistros++;
            regDenominacion[totalRegistros] = denominacion;
            regCantidad[totalRegistros] = cantidad;
            mostrarRegistros();
            txtRegistradora.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.");
        }
    }

    private void mostrarRegistros() {
        String[] Stregistro = new String[totalRegistros + 1];
        for (int i = 0; i <= totalRegistros; i++) {
           Stregistro[i]=" - Cantidad: " +regCantidad[i] +"$"+ regDenominacion[i] ;
           
        }
        lstRegistros.setListData(Stregistro);
    }
    private void DevolverValor() {
        dtm.setRowCount(0);
        try {
            int monto = Integer.parseInt(txtvalor.getText());
            int[] existencia = new int[opciones.length];
            for (int i = 0; i < opciones.length; i++) {
                existencia[i] = 0;
            }
            for (int i = 0; i <= totalRegistros; i++) {
                for (int j = 0; j < opciones.length; j++) {
                    if (regDenominacion[i] == opciones[j]) {
                        existencia[j] += regCantidad[i];
                        break;
                    }
                }
            }

            int Pendiente = monto;
            for (int i = 0; i < opciones.length; i++) {
                int denominacion = opciones[i];
                int disponible = existencia[i];
                int necesito = Pendiente / denominacion;
                int Uso;
                if (necesito > disponible) {
                    Uso = disponible;
                } else {
                    Uso= necesito;
                }
                if (Uso > 0) {
                    dtm.addRow(new Object[]{"$" + denominacion, Denominacion.get(denominacion), Uso});
                    Pendiente -= Uso * denominacion;
                }
            }
            if (Pendiente != 0) {
                JOptionPane.showMessageDialog(this, "No es posible darle una devuelta exacta.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido.");
        }
    }
}



        
    


        














    


    


    


