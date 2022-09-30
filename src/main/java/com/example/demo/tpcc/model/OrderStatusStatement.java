package com.example.demo.tpcc.model;

import com.example.demo.tpcc.dataBaseConfig.Database;
import com.example.demo.tpcc.dataBaseConfig.Logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderStatusStatement {
    Connection con = Database.createCon();

    public Order orderStatusTransaction(String w_id, String d_id, String c_id, String c_last, boolean c_by_name) {
        long namecnt, o_id = -1;

        try {
            if (c_by_name) {
                ///////// First Statement
                PreparedStatement ps46 = con.prepareStatement(
                        "SELECT count(c_id) AS namecnt " +
                                "  FROM tpcc_customer " +
                                " WHERE c_last = '" + c_last + "' " +
                                "   AND c_d_id = '" + d_id + "' " +
                                "   AND c_w_id = '" + w_id + "' ");
                ResultSet rs = ps46.executeQuery();

                if (!rs.next()) {
                    throw new Exception("C_LAST=" + c_last + " C_D_ID=" + d_id + " C_W_ID=" + w_id + " not found!");
                }
                namecnt = rs.getLong("namecnt");
                rs.close();

                ///////// Second Statement
                PreparedStatement ps66 = con.prepareStatement(
                        "SELECT c_balance, c_first, c_middle, c_id " +
                                "  FROM tpcc_customer " +
                                " WHERE c_last = '" + c_last + "' " +
                                "   AND c_d_id = '" + d_id + "' " +
                                "   AND c_w_id = '" + w_id + "' " +
                                " ORDER BY c_first ASC");
                rs = ps66.executeQuery();

                if (!rs.next()) {
                    throw new Exception("C_LAST=" + c_last + " C_D_ID=" + d_id + " C_W_ID=" + w_id + " not found!");
                }

                ///////// Third Statement
                PreparedStatement ps96 = con.prepareStatement(
                        "SELECT c_balance, c_first, c_middle, c_last " +
                                "  FROM tpcc_customer " +
                                " WHERE c_id = '" + c_id + "' " +
                                "   AND c_d_id = '" + d_id + "' " +
                                "   AND c_w_id = '" + w_id + "' ");
                ps96.executeQuery();

                ///////// Forth Statement
                PreparedStatement ps122 = con.prepareStatement(
                        "SELECT * FROM " +
                                "   (SELECT o_id, o_carrier_id, o_entry_d " +
                                "      FROM tpcc_orderr " +
                                "     WHERE o_w_id = '" + w_id + "' " +
                                "       AND o_d_id = '" + d_id + "' " +
                                "       AND o_c_id = '" + c_id + "' " +
                                "     ORDER BY o_id DESC) " +
                                " WHERE rownum = 1");
                ps122.executeQuery();
                rs.close();

                ///////// Fifth Statement
                PreparedStatement ps143 = con.prepareStatement(
                        "SELECT ol_i_id, ol_supply_w_id, ol_quantity," +
                                "       ol_amount, ol_delivery_d" +
                                "  FROM tpcc_order_line" +
                                " WHERE ol_o_id = '" + o_id + "' " +
                                "   AND ol_d_id = '" + d_id + "' " +
                                "   AND ol_w_id = '" + w_id + "' ");
                rs = ps143.executeQuery();
                rs.close();
                rs = null;
                con.commit();
            }
        } catch (Exception e) {
            Logging.error(" ORDER STATUS  EXCEPTION " + e.toString());
        }
        return new Order(o_id, c_id);
    }
}