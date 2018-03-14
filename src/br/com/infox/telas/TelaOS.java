/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ronal
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // cria variavel para armazenar o radio button selecionado
    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisarCliente() {
        String sql = "select idcliente as Id, nomecli as Nome, fonecli as Fone from clientes where nomecli like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqCliOs.getText() + "%");
            rs = pst.executeQuery();
            tableCLiOS.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarCampos() {
        int setar = tableCLiOS.getSelectedRow();
        txtIdCliOs.setText(tableCLiOS.getModel().getValueAt(setar, 0).toString());
    }

    private void emitir_os() {
        String sql = "insert into os (tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcliente) values (?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbxCliOS.getSelectedItem().toString());
            pst.setString(3, txtEquiOS.getText());
            pst.setString(4, txtDefos.getText());
            pst.setString(5, txtServOS.getText());
            pst.setString(6, txtTecos.getText());
            pst.setString(7, txtVtOS.getText().replace(",", "."));//.replace substitui a virgula pelo ponto

            pst.setString(8, txtIdCliOs.getText());

            //campos obrigatorios
            if (txtIdCliOs.getText().isEmpty() || (txtEquiOS.getText().isEmpty() || (txtDefos.getText().isEmpty()))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
                    txtIdCliOs.setText(null);
                    txtEquiOS.setText(null);
                    txtDefos.setText(null);
                    txtServOS.setText(null);
                    txtTecos.setText(null);
                    txtVtOS.setText(null);
                    txtPesqCliOs.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_os() {
        String num_os = JOptionPane.showInputDialog("Número da OS");
        String sql = "select * from os where idos = " + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNumOS.setText(rs.getString(1));
                txtDataOS.setText(rs.getString(2));
                //setando radiobutton
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rdbOrcOs.setSelected(true);
                    tipo = "OS";
                } else {
                    rdbOrdemOS.setSelected(true);
                    tipo = "Orçamento";
                }
                cbxCliOS.setSelectedItem(rs.getString(4));
                txtEquiOS.setText(rs.getString(5));
                txtDefos.setText(rs.getString(6));
                txtServOS.setText(rs.getString(7));
                txtTecos.setText(rs.getString(8));
                txtVtOS.setText(rs.getString(9));
                txtIdCliOs.setText(rs.getString(10));

                btnAddOS.setEnabled(false);
                txtPesqCliOs.setEnabled(false);
                tableCLiOS.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada!");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS inválida!");
            //System.out.println(e);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void editar_os() {
        String sql = "update os set tipo=?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where idos=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbxCliOS.getSelectedItem().toString());
            pst.setString(3, txtEquiOS.getText());
            pst.setString(4, txtDefos.getText());
            pst.setString(5, txtServOS.getText());
            pst.setString(6, txtTecos.getText());
            pst.setString(7, txtVtOS.getText().replace(",", "."));//.replace substitui a virgula pelo ponto
            pst.setString(8, txtNumOS.getText());

            //campos obrigatorios
            if (txtIdCliOs.getText().isEmpty() || (txtEquiOS.getText().isEmpty() || (txtDefos.getText().isEmpty()))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso!");
                    //limpara os campos
                    txtNumOS.setText(null);
                    txtDataOS.setText(null);
                    txtIdCliOs.setText(null);
                    txtEquiOS.setText(null);
                    txtDefos.setText(null);
                    txtServOS.setText(null);
                    txtTecos.setText(null);
                    txtVtOS.setText(null);
                    txtPesqCliOs.setText(null);

                    //habilitar os objetos
                    txtPesqCliOs.setEnabled(true);
                    btnAddOS.setEnabled(true);
                    tableCLiOS.setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluir_os() {

        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from os where idos=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluida com sucesso!");
                    //limpara os campos
                    txtNumOS.setText(null);
                    txtDataOS.setText(null);
                    txtIdCliOs.setText(null);
                    txtEquiOS.setText(null);
                    txtDefos.setText(null);
                    txtServOS.setText(null);
                    txtTecos.setText(null);
                    txtVtOS.setText(null);
                    txtPesqCliOs.setText(null);

                    //habilitar os objetos
                    txtPesqCliOs.setEnabled(true);
                    btnAddOS.setEnabled(true);
                    tableCLiOS.setVisible(true);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    //imprimir os
    private void imprimir_os(){
         // imprimindo uma os
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta OS?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                //usando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("idos", Integer.parseInt(txtNumOS.getText()));
                JasperPrint print = JasperFillManager.fillReport("C:/Reports/os.jasper", filtro, conexao);

                //exibe o relatorio atraves da classe jasperviewer
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNumOS = new javax.swing.JTextField();
        txtDataOS = new javax.swing.JTextField();
        rdbOrcOs = new javax.swing.JRadioButton();
        rdbOrdemOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cbxCliOS = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtPesqCliOs = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdCliOs = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCLiOS = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtEquiOS = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDefos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtServOS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTecos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtVtOS = new javax.swing.JTextField();
        btnAddOS = new javax.swing.JButton();
        btnPesqOS = new javax.swing.JButton();
        btnEditOs = new javax.swing.JButton();
        btnExclOS = new javax.swing.JButton();
        btnIMpOS = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(504, 454));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtNumOS.setEditable(false);

        txtDataOS.setEditable(false);
        txtDataOS.setFont(new java.awt.Font("Arial Black", 1, 10)); // NOI18N

        buttonGroup1.add(rdbOrcOs);
        rdbOrcOs.setText("Orçamento");
        rdbOrcOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbOrcOsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbOrdemOS);
        rdbOrdemOS.setText("OS");
        rdbOrdemOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbOrdemOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(rdbOrcOs)
                        .addGap(18, 18, 18)
                        .addComponent(rdbOrdemOS))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumOS, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtDataOS, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbOrdemOS)
                    .addComponent(rdbOrcOs))
                .addGap(19, 19, 19))
        );

        jLabel3.setText("Situação");

        cbxCliOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Na Bancada", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel2.setToolTipText("");

        txtPesqCliOs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqCliOsKeyReleased(evt);
            }
        });

        jLabel4.setText("Pesquisa");

        jLabel5.setText("*ID");

        txtIdCliOs.setEditable(false);

        tableCLiOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ));
        tableCLiOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCLiOSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCLiOS);
        if (tableCLiOS.getColumnModel().getColumnCount() > 0) {
            tableCLiOS.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPesqCliOs, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtIdCliOs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesqCliOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtIdCliOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento");

        txtEquiOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEquiOSActionPerformed(evt);
            }
        });

        jLabel7.setText("*Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        jLabel10.setText("Valor Total");

        txtVtOS.setText("0");

        btnAddOS.setText("Adicionar");
        btnAddOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOSActionPerformed(evt);
            }
        });

        btnPesqOS.setText("Pesquisar");
        btnPesqOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqOSActionPerformed(evt);
            }
        });

        btnEditOs.setText("Editar");
        btnEditOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOsActionPerformed(evt);
            }
        });

        btnExclOS.setText("Excluir");
        btnExclOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExclOSActionPerformed(evt);
            }
        });

        btnIMpOS.setText("Imprimir");
        btnIMpOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIMpOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTecos, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtVtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtServOS, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDefos, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(btnPesqOS)
                                .addGap(44, 44, 44)
                                .addComponent(btnEditOs))
                            .addComponent(txtEquiOS, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addGap(20, 20, 20)
                                .addComponent(cbxCliOS, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddOS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExclOS)
                .addGap(63, 63, 63)
                .addComponent(btnIMpOS)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCliOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEquiOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDefos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtServOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTecos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtVtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnAddOS)
                    .addComponent(btnPesqOS)
                    .addComponent(btnEditOs)
                    .addComponent(btnExclOS)
                    .addComponent(btnIMpOS))
                .addGap(29, 29, 29))
        );

        setBounds(0, 0, 617, 454);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEquiOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEquiOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEquiOSActionPerformed

    private void txtPesqCliOsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqCliOsKeyReleased
        //chama o metodo pesquisarcliente
        pesquisarCliente();
    }//GEN-LAST:event_txtPesqCliOsKeyReleased

    private void tableCLiOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCLiOSMouseClicked
        // chama o metodo setar campos
        setarCampos();
    }//GEN-LAST:event_tableCLiOSMouseClicked

    private void rdbOrcOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbOrcOsActionPerformed
        // texto a variavel tipo se for selecionado radio button
        tipo = "Orçamento";
    }//GEN-LAST:event_rdbOrcOsActionPerformed

    private void rdbOrdemOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbOrdemOSActionPerformed
        // texto a variavel tipo se for selecionado radio button
        tipo = "OS";
    }//GEN-LAST:event_rdbOrdemOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // qndo carrega o form, marcar o radio button orcamento
        rdbOrcOs.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAddOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOSActionPerformed
        // chama o metodo emitir os
        emitir_os();
    }//GEN-LAST:event_btnAddOSActionPerformed

    private void btnPesqOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqOSActionPerformed
        //chama o metodo pesquisar
        pesquisar_os();
    }//GEN-LAST:event_btnPesqOSActionPerformed

    private void btnEditOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOsActionPerformed
        // chama o metodo editaros
        editar_os();
    }//GEN-LAST:event_btnEditOsActionPerformed

    private void btnExclOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExclOSActionPerformed
        //chama o metodo excluir
        excluir_os();
    }//GEN-LAST:event_btnExclOSActionPerformed

    private void btnIMpOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIMpOSActionPerformed
        // chama o metodo imprimiros
        imprimir_os();
    }//GEN-LAST:event_btnIMpOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOS;
    private javax.swing.JButton btnEditOs;
    private javax.swing.JButton btnExclOS;
    private javax.swing.JButton btnIMpOS;
    private javax.swing.JButton btnPesqOS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxCliOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdbOrcOs;
    private javax.swing.JRadioButton rdbOrdemOS;
    private javax.swing.JTable tableCLiOS;
    private javax.swing.JTextField txtDataOS;
    private javax.swing.JTextField txtDefos;
    private javax.swing.JTextField txtEquiOS;
    private javax.swing.JTextField txtIdCliOs;
    private javax.swing.JTextField txtNumOS;
    private javax.swing.JTextField txtPesqCliOs;
    private javax.swing.JTextField txtServOS;
    private javax.swing.JTextField txtTecos;
    private javax.swing.JTextField txtVtOS;
    // End of variables declaration//GEN-END:variables
}
