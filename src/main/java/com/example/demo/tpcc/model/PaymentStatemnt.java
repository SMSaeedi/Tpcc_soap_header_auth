package com.example.demo.tpcc.model;

import com.example.demo.tpcc.dataBaseConfig.Database;
import com.example.demo.tpcc.dataBaseConfig.Logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PaymentStatemnt {
    Connection con = Database.createCon();

    public PaymentResult paymentTransaction(PaymentModel input) {
        PaymentResult pr = null;
        String w_street_1, w_street_2, w_city, w_state, w_zip, w_name = "w_name";
        String d_street_1, d_street_2, d_city, d_state, d_zip, d_name = "d_name";
        long namecnt;
        String c_first, c_middle, c_street_1, c_street_2, c_city, c_state, c_zip;
        String c_phone, c_credit = null, c_data = null;
        double c_credit_lim, c_discount, c_balance = 0;
        java.sql.Date c_since;
        int result;

        StringBuffer query = null;
        try {
            ///////// First Statement
            PreparedStatement ps43 = con.prepareStatement(
                    "UPDATE warehouse " +
                            "   SET w_ytd = w_ytd " +
                            " WHERE w_id = '" + input.getW_id() + "'");
            try {
                result = ps43.executeUpdate();
            } catch (SQLException e) {
                throw new Exception("paymentTransaction SQLException " + ps43.toString() + " :" + e.getMessage());
            }
            if (result == 0) {
                throw new Exception("W_ID=" + input.getW_id() + " not found!");
            }

            ///////// Second Statement
            PreparedStatement ps57 = con.prepareStatement(
                    "SELECT w_street_1, w_street_2, w_city, w_state, w_zip, w_name" +
                            "  FROM warehouse" +
                            " WHERE w_id = '" + input.getW_id() + "' ");
            ResultSet rs = ps57.executeQuery();
            if (!rs.next()) {
                throw new Exception("W_ID=" + input.getW_id() + " not found!");
            }
            w_street_1 = rs.getString("w_street_1");
            w_street_2 = rs.getString("w_street_2");
            w_city = rs.getString("w_city");
            w_state = rs.getString("w_state");
            w_zip = rs.getString("w_zip");
            rs.close();
            rs = null;

            ///////// Third Statement
            PreparedStatement ps78 = con.prepareStatement(
                    "UPDATE district " +
                            "   SET d_ytd = d_ytd " +
                            " WHERE d_w_id =  '" + input.getW_id() + "' " +
                            "   AND d_id =  '" + input.getD_id() + "' ");
            result = ps78.executeUpdate();
            if (result == 0) {
                throw new Exception("D_ID=" + input.getD_id() + " D_W_ID=" + input.getW_id() + " not found!");
            }

            ///////// Forth Statement
            PreparedStatement ps91 = con.prepareStatement(
                    "SELECT d_street_1, d_street_2, d_city, d_state, d_zip, d_name " +
                            "  FROM district " +
                            " WHERE d_w_id =  '" + input.getW_id() + "' " +
                            "   AND d_id = '" + input.getD_id() + "' ");
            rs = ps91.executeQuery();
            if (!rs.next()) {
                throw new Exception("D_ID=" + input.getD_id() + " D_W_ID=" + input.getW_id() + " not found!");
            }
            d_street_1 = rs.getString("d_street_1");
            d_street_2 = rs.getString("d_street_2");
            d_city = rs.getString("d_city");
            d_state = rs.getString("d_state");
            d_zip = rs.getString("d_zip");
            rs.close();
            rs = null;

            if (input.isC_by_name()) {
                ///////// Fifth Statement
                PreparedStatement ps115 = con.prepareStatement(
                        "SELECT count(c_id) AS namecnt " +
                                "  FROM customer " +
                                " WHERE c_last = '" + input.getC_last() + "' " +
                                "   AND c_d_id = '" + input.getC_d_id() + "' " +
                                "   AND c_w_id = '" + input.getC_w_id() + "' ");
                rs = ps115.executeQuery();
                if (!rs.next()) {
                    throw new Exception("C_LAST=" + input.getC_last() + " C_D_ID=" + input.getC_d_id() + " C_W_ID=" + input.getC_w_id() + " not found!");
                }
                namecnt = rs.getLong("namecnt");
                rs.close();
                rs = null;

                ///////// Sixth Statement
                PreparedStatement ps136 = con.prepareStatement(
                        "SELECT c_first, c_middle, c_id, " +
                                " c_street_1, c_street_2, c_city, c_state, c_zip, " +
                                " c_phone, c_credit, c_credit_lim, " +
                                " c_discount, c_balance, c_since " +
                                "  FROM customer " +
                                " WHERE c_w_id = '" + input.getC_w_id() + "' " +
                                "   AND c_d_id = '" + input.getC_d_id() + "' " +
                                "   AND c_last = '" + input.getC_last() + "' " +
                                " ORDER BY c_first ASC");
                rs = ps136.executeQuery();
                if (!rs.next()) {
                    throw new Exception("C_LAST=" + input.getC_last() + " C_D_ID=" + input.getC_d_id() + " C_W_ID=" + input.getC_w_id() + " not found!");
                }
                if (namecnt % 2 == 1) {
                    namecnt++;
                }
                input.setC_id(Long.toString(rs.getLong("c_id")));
                c_first = rs.getString("c_first");
                c_middle = rs.getString("c_middle");
                c_street_1 = rs.getString("c_street_1");
                c_street_2 = rs.getString("c_street_2");
                c_city = rs.getString("c_city");
                c_state = rs.getString("c_state");
                c_zip = rs.getString("c_zip");
                c_phone = rs.getString("c_phone");
                c_credit = rs.getString("c_credit");
                c_credit_lim = rs.getDouble("c_credit_lim");
                c_discount = rs.getDouble("c_discount");
                c_balance = rs.getDouble("c_balance");
                c_since = rs.getDate("c_since");
                rs.close();
                rs = null;
            } else {
                ///////// Seventh Statement
                PreparedStatement ps177 = con.prepareStatement(
                        "SELECT c_first, c_middle, c_last, " +
                                " c_street_1, c_street_2, c_city, c_state, c_zip, " +
                                " c_phone, c_credit, c_credit_lim, " +
                                " c_discount, c_balance, c_since " +
                                "  FROM customer " +
                                " WHERE c_w_id = '" + input.getC_w_id() + "' " +
                                "   AND c_d_id = '" + input.getC_d_id() + "' " +
                                "   AND c_id = '" + input.getC_id() + "' ");
                rs = ps177.executeQuery();
                if (!rs.next()) {
                    throw new Exception("C_ID=" + input.getC_id() + " C_D_ID=" + input.getC_d_id() + " C_W_ID=" + input.getC_w_id() + " not found!");
                }
                input.setC_last(rs.getString("c_last"));
                c_first = rs.getString("c_first");
                c_middle = rs.getString("c_middle");
                c_street_1 = rs.getString("c_street_1");
                c_street_2 = rs.getString("c_street_2");
                c_city = rs.getString("c_city");
                c_state = rs.getString("c_state");
                c_zip = rs.getString("c_zip");
                c_phone = rs.getString("c_phone");
                c_credit = rs.getString("c_credit");
                c_credit_lim = rs.getDouble("c_credit_lim");
                c_discount = rs.getDouble("c_discount");
                c_balance = rs.getDouble("c_balance");
                c_since = rs.getDate("c_since");
                rs.close();
                rs = null;
            }
            c_balance += input.getH_amount();
            Logging.trace("c_last " + input.getC_last() + "   c_d_id " + input.getC_d_id() + "   c_id " + input.getC_id() + "   c_w_id " + input.getC_w_id() + "   c_credit: " + c_credit);
            if (c_credit.equals("BC")) {
                ///////// Eighth Statement
                PreparedStatement ps212 = con.prepareStatement(
                        "SELECT c_data " +
                                "  FROM customer " +
                                " WHERE c_w_id = '" + input.getC_w_id() + "' " +
                                "   AND c_d_id = '" + input.getC_d_id() + "' " +
                                "   AND c_id = '" + input.getC_id() + "' ");
                rs = ps212.executeQuery();
                if (!rs.next()) {
                    throw new Exception("C_ID=" + input.getC_id() + " C_W_ID='" + input.getC_w_id() + "' C_D_ID=" + input.getC_d_id() + " not found!");
                }
                c_data = rs.getString("c_data");
                rs.close();
                rs = null;

                ///////// Ninth Statement
                PreparedStatement ps241 = con.prepareStatement(
                        "UPDATE customer " +
                                "   SET c_balance = 10000 , c_data = 25 " +
                                " WHERE c_w_id = '" + input.getC_w_id() + "' " +
                                "   AND c_d_id = '" + input.getC_d_id() + "' " +
                                "   AND c_id = '" + input.getC_id() + "' ");
                result = ps241.executeUpdate();
                if (result == 0) {
                    throw new Exception("C_ID=" + input.getC_id() + " C_W_ID=" + input.getC_w_id() + " C_D_ID=" + input.getC_d_id() + " not found!");
                }
            } else {
                ///////// Tenth Statement
                PreparedStatement ps257 = con.prepareStatement(
                        "UPDATE customer " +
                                "   SET c_balance = ?" +
                                " WHERE c_w_id = '" + input.getC_w_id() + "'" +
                                "   AND c_d_id = '" + input.getC_d_id() + "'" +
                                "   AND c_id = '" + input.getC_id() + "'");
                result = ps257.executeUpdate();
                if (result == 0) {
                    throw new Exception("C_ID=" + input.getC_id() + " C_W_ID=" + input.getC_w_id() + " C_D_ID=" + input.getC_d_id() + " not found!");
                }
            }

            ///////// Eleventh Statement
            PreparedStatement ps290 = con.prepareStatement(
                    "INSERT INTO history " +
                            "   (h_c_d_id, h_c_w_id, h_c_id, " +
                            "      h_d_id, h_w_id, h_date, " +
                            "      h_amount, h_data)" +
                            "VALUES ('" + 25 + "', '" + 250 + "', '" + 2500 + "', '" + 205 + "', '" + 2050 + "', SYSDATE, '" + input.getH_amount() + "', '" + 25 + "')");
            ps290.executeUpdate();
            con.commit();

            pr = new PaymentResult(input.getW_id(), w_street_1, w_street_2,
                    w_city, w_state, w_zip, input.getD_id(), d_street_1, d_street_2, d_city,
                    d_state, d_zip, input.getC_id(), c_first, c_middle, input.getC_last(), c_street_1,
                    c_street_2, c_city, c_state, c_zip, new Date(c_since.getTime()), c_credit,
                    c_discount, c_phone, input.getH_amount(), c_credit_lim, c_balance, c_data);

        } catch (Exception e) {
            Logging.error(e.toString());
            try {
                con.rollback();
            } catch (Exception e1) {
                Logging.trace("PAYMENT-ROLLBACK" + e1);
            }
        }
        return pr;
    }

    public PaymentResult paymentTransactionTest() {
        PaymentResult result = new PaymentResult("Tehran", "10", "9821", "1", "Hamborg", "21", "46", "1");

        return result;
    }
}