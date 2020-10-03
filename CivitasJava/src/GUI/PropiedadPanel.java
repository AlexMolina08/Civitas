/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import civitas.TituloPropiedad;
/**
 *
 * @author alex
 */
class PropiedadPanel extends javax.swing.JPanel {

    /**
     * Creates new form PropiedadPanel
     */
    PropiedadPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldCasasYHoteles = new javax.swing.JTextField();
        jTextFieldHipotecada = new javax.swing.JTextField();
        jLabelNombre = new javax.swing.JLabel();
        jLabelCasasYHoteles = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jLabelHipotecada = new javax.swing.JLabel();

        jTextFieldCasasYHoteles.setFont(new java.awt.Font("Laksaman", 0, 15)); // NOI18N
        jTextFieldCasasYHoteles.setText("jTextField2");
        jTextFieldCasasYHoteles.setEnabled(false);

        jTextFieldHipotecada.setFont(new java.awt.Font("Laksaman", 0, 15)); // NOI18N
        jTextFieldHipotecada.setText("jTextField3");
        jTextFieldHipotecada.setEnabled(false);

        jLabelNombre.setFont(new java.awt.Font("Laksaman", 0, 15)); // NOI18N
        jLabelNombre.setText("Nombre");

        jLabelCasasYHoteles.setFont(new java.awt.Font("Laksaman", 0, 15)); // NOI18N
        jLabelCasasYHoteles.setText("Casas y Hoteles");

        jTextFieldNombre.setFont(new java.awt.Font("Laksaman", 0, 15)); // NOI18N
        jTextFieldNombre.setText("123451234512345");
        jTextFieldNombre.setEnabled(false);

        jLabelHipotecada.setFont(new java.awt.Font("Laksaman", 0, 15)); // NOI18N
        jLabelHipotecada.setText("Hipotecada");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelNombre)
                .addGap(5, 5, 5)
                .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabelCasasYHoteles)
                .addGap(3, 3, 3)
                .addComponent(jTextFieldCasasYHoteles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelHipotecada)
                .addGap(4, 4, 4)
                .addComponent(jTextFieldHipotecada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNombre)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldCasasYHoteles, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelHipotecada)
                        .addComponent(jTextFieldHipotecada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelCasasYHoteles)))
                .addGap(8, 8, 8))
        );
    }// </editor-fold>//GEN-END:initComponents

    //---------------------------------------------------------
    //Métodos
    void setPropiedad(TituloPropiedad titulo){
        tituloPropiedad = titulo;
        
        //Actualizar texto
        int num = titulo.cantidadCasasHoteles();
        String hipotecada = (titulo.getHipotecado())? "SI" : "NO";
        
        this.jTextFieldNombre.setText(titulo.getNombre());
        this.jTextFieldCasasYHoteles.setText(Integer.toString(num));
        this.jTextFieldHipotecada.setText(hipotecada);
    }

    //---------------------------------------------------------
    //Atributos
    private TituloPropiedad tituloPropiedad;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelCasasYHoteles;
    private javax.swing.JLabel jLabelHipotecada;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JTextField jTextFieldCasasYHoteles;
    private javax.swing.JTextField jTextFieldHipotecada;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables
}
