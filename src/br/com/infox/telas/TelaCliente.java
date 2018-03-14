/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
//importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ronal
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionarCliente() {
        String sql = "insert into clientes(nomecli,endcli,fonecli,emailcli)values (?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCli.getText());
            pst.setString(2, txtEndCli.getText());
            pst.setString(3, txtTelCli.getText());
            pst.setString(4, txtEmailCli.getText());

            //validação dos campos obrigatorios
            if (txtNomeCli.getText().isEmpty() || (txtEndCli.getText().isEmpty()) || (txtTelCli.getText().isEmpty()) || (txtEmailCli.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {

                //atualiza a tabela de usuarios, com os dados do formulario
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
                    //limpam os campos
                    txtNomeCli.setText(null);
                    txtEndCli.setText(null);
                    txtTelCli.setText(null);
                    txtEmailCli.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisarClientePorNome() {

        String sql = "select * from clientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o ?
            //atencao ao % que e a continuação da string sql
            pst.setString(1, txtPesqCLi.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para setar os campos da tabela no formulario
    private void setarCampos() {
        int setar = tblClientes.getSelectedRow();
        txtIdCli.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtNomeCli.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtEndCli.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtTelCli.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtEmailCli.setText(tblClientes.getModel().getValueAt(setar, 4).toString());

        //desabilita o botao adicionar
        btnAdiconarCli.setEnabled(false);
    }

    private void editarCliente() {
        String sql = "update clientes set nomecli=?,endcli=?,fonecli=?,emailcli=? where idcliente=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCli.getText());
            pst.setString(2, txtEndCli.getText());
            pst.setString(3, txtTelCli.getText());
            pst.setString(4, txtEmailCli.getText());
            pst.setString(5, txtIdCli.getText());
            // pst.setString(6, (String) txtiduser);
            //validação dos campos obrigatorios
            if (txtNomeCli.getText().isEmpty() || (txtTelCli.getText().isEmpty()) || (txtEmailCli.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {

                //atualiza a tabela de usuarios, com os dados do formulario
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso!");
                    //limpam os campos
                    txtNomeCli.setText(null);
                    txtEndCli.setText(null);
                    txtTelCli.setText(null);
                    txtEmailCli.setText(null);

                    //reabilitar o botao adicionar
                    btnAdiconarCli.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void delete() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir os dados?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from clientes where idcliente=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdCli.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                    //limpam os campos
                    txtNomeCli.setText(null);
                    txtEndCli.setText(null);
                    txtTelCli.setText(null);
                    txtEmailCli.setText(null);
                }
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

        jLabel1 = new javax.swing.JLabel();
        txtNomeCli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtEndCli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelCli = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmailCli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnEditarCli = new javax.swing.JButton();
        btnExcluirCli = new javax.swing.JButton();
        btnAdiconarCli = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtPesqCLi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtIdCli = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(504, 454));

        jLabel1.setText("*Nome");

        jLabel2.setText("*Endereço");

        jLabel3.setText("*Telefone");

        jLabel4.setText("*Email");

        jLabel5.setText("*Campos Obrigatórios");

        btnEditarCli.setText("Editar");
        btnEditarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCliActionPerformed(evt);
            }
        });

        btnExcluirCli.setText("Excluir");
        btnExcluirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirCliActionPerformed(evt);
            }
        });

        btnAdiconarCli.setText("Adicionar");
        btnAdiconarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdiconarCliActionPerformed(evt);
            }
        });

        jLabel6.setText("Pesquisar");

        txtPesqCLi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqCLiKeyReleased(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel7.setText("ID");

        txtIdCli.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEndCli, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(47, 47, 47)
                                .addComponent(txtEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(btnAdiconarCli)
                                .addGap(104, 104, 104)
                                .addComponent(btnEditarCli)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExcluirCli))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesqCLi, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesqCLi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEndCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnEditarCli)
                    .addComponent(btnExcluirCli)
                    .addComponent(btnAdiconarCli))
                .addContainerGap(256, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdiconarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdiconarCliActionPerformed
        // chama metodo adicionar
        adicionarCliente();
    }//GEN-LAST:event_btnAdiconarCliActionPerformed
    // enquanto for digitando 
    private void txtPesqCLiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqCLiKeyReleased
        //chama o metodo pesquisar
        pesquisarClientePorNome();
    }//GEN-LAST:event_txtPesqCLiKeyReleased

    //metodo que sera usado para setar os campos da tabela clicando com o mouse
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // chama o metodo setar
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnEditarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCliActionPerformed
        // chama o metodo editar
        editarCliente();
    }//GEN-LAST:event_btnEditarCliActionPerformed

    private void btnExcluirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirCliActionPerformed
        // chama o metodo delete
        delete();
    }//GEN-LAST:event_btnExcluirCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdiconarCli;
    private javax.swing.JButton btnEditarCli;
    private javax.swing.JButton btnExcluirCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtEmailCli;
    private javax.swing.JTextField txtEndCli;
    private javax.swing.JTextField txtIdCli;
    private javax.swing.JTextField txtNomeCli;
    private javax.swing.JTextField txtPesqCLi;
    private javax.swing.JTextField txtTelCli;
    // End of variables declaration//GEN-END:variables
}
