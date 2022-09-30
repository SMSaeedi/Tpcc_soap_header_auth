package com.example.demo.tpcc.model;

import com.example.demo.tpcc.dataBaseConfig.Database;
import com.example.demo.tpcc.dataBaseConfig.Logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewOrderStatement {
    Connection con = Database.createCon();

    public ShortOrder newOrderTransaction(String w_id, String d_id, String c_id) {
        long o_id = -1;
        double total_amount = 0;
        boolean newOrderRowInserted;

        StringBuffer query = null;
        try {
            ///////// First Statement
            PreparedStatement ps56 = con.prepareStatement(
                    "SELECT c_discount, c_last, c_credit, w_tax " +
                            "  FROM tpcc_customer, tpcc_warehouse " +
                            " WHERE w_id = '" + w_id + "' " +
                            "   AND c_d_id = '" + d_id + "' " +
                            "   AND c_id = '" + c_id + "' ");
            ResultSet rs = ps56.executeQuery();

            if (!rs.next()) {
                throw new Exception("W_ID=" + w_id + " C_D_ID=" + d_id + " C_ID=" + c_id + " not found!");
            }
            rs.close();

            int j = 0;
            newOrderRowInserted = false;
            while (!newOrderRowInserted && j++ < 10) {
                ///////// Second Statement
                PreparedStatement ps81 = con.prepareStatement(
                        "SELECT d_next_o_id, d_tax " +
                                "  FROM tpcc_district " +
                                " WHERE d_id = '" + d_id + "' " +
                                "   AND d_w_id = '" + w_id + "' ");
                rs = ps81.executeQuery();

                if (!rs.next()) {
                    throw new Exception("D_ID=" + d_id + " D_W_ID=" + w_id + " not found!");
                }
                rs.close();

                try {
                    ///////// Third Statement
                    PreparedStatement ps103 = con.prepareStatement(
                            "INSERT INTO tpcc_new_order (no_o_id, no_d_id, no_w_id)" +
                                    " VALUES (?, ?, ?)");
                    ps103.executeUpdate();
                    newOrderRowInserted = true;
                } catch (SQLException e2) {
                    Logging.error("The row was already on table tpcc_new_order. Restarting..." + e2.getMessage());
                }
            }

            ///////// Forth Statement
            PreparedStatement ps119 = con.prepareStatement(
                    "UPDATE tpcc_district SET d_next_o_id = d_next_o_id + 1" +
                            " WHERE d_id = '" + d_id + "'" +
                            "  AND d_w_id = '" + w_id + "'");
            int result = ps119.executeUpdate();
            if (result == 0) {
                throw new Exception("D_ID=" + d_id + " D_W_ID=" + w_id + " not found!");
            }

            ///////// Fifth Statement
            PreparedStatement ps180 = con.prepareStatement(
                    "SELECT s_quantity, s_data," +
                            "  s_dist_01, s_dist_02, s_dist_03, s_dist_04, s_dist_05," +
                            " s_dist_06, s_dist_07, s_dist_08, s_dist_09, s_dist_10" +
                            "  FROM tpcc_stock");
            rs = ps180.executeQuery();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ShortOrder(o_id, total_amount);
    }
}