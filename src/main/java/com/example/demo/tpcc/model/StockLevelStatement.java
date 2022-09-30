package com.example.demo.tpcc.model;


import com.example.demo.tpcc.dataBaseConfig.Database;
import com.example.demo.tpcc.dataBaseConfig.Logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StockLevelStatement {
    Connection con = Database.createCon();

    public long stockLevelTransaction(PaymentResult input) {
        long o_id, stock_count;

        try {
            ///////// First Statement
            PreparedStatement ps33 = con.prepareStatement(
                    "SELECT o_id " +
                            "  FROM district " +
                            " WHERE d_w_id = '" + input.getW_id() + "' " +
                            "   AND d_id = '" + input.getD_id() + "' ");
            ResultSet rs = ps33.executeQuery();
            if (!rs.next()) {
                throw new Exception("D_W_ID=" + input.getW_id() + " D_ID=" + input.getD_id() + " not found!");
            }
            o_id = rs.getLong("d_next_o_id");
            rs.close();
            ps33.close();
            rs = null;

            ///////// Second Statement
            PreparedStatement ps57 = con.prepareStatement("SELECT COUNT(DISTINCT ('" + input.getS_i_id() + "' )) AS stock_count " +
                    "  FROM tpcc_order_line, tpcc_stock " +
                    " WHERE s_i_id = ol_i_id " +
                    "   AND s_quantity < '" + 500 + "' " +
                    "   AND ol_w_id = '" + input.getOl_d_id() + "' " +
                    "   AND ol_d_id = '" + input.getOl_d_id() + "' " +
                    "   AND ol_o_id < '" + 50 + "'  " +
                    "   AND ol_o_id >= '" + 2 + "'  " +
                    "   AND s_w_id = '" + input.getS_w_id() + "'  ");
            rs = ps57.executeQuery();
            if (!rs.next()) {
                throw new Exception("OL_W_ID=" + input.getW_id() + " OL_D_ID=" + input.getD_id() + " OL_O_ID=" + o_id + " (...) not found!");
            }
            stock_count = rs.getLong("stock_count");
            rs.close();
            rs = null;
            ps57.close();
            con.commit();
            return stock_count;
        } catch (Exception e) {
            Logging.error(" STOCK LEVEL " + e.toString());
            return -1;
        }
    }

    public long stockLevelTransactionTest(String w_id, String d_id, long threshold) {
        w_id = "12";
        d_id = "25";

        threshold = Long.parseLong(w_id + d_id);

        return threshold;
    }
}