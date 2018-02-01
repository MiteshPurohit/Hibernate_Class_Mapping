/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author f1cmpica-1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            Configuration cfg = new Configuration().configure();
            SessionFactory sf = cfg.buildSessionFactory();
            Session s = sf.openSession();
            Transaction t;

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            while (true) {
                System.out.println("------------- Menu --------------");
                System.out.println("---------------------------------");
                System.out.println("1.) INSERT record.....");
                System.out.println("2.) UPDATE for ID.....");
                System.out.println("3.) DELETE for ID.....");
                System.out.println("4.) DISPLAY for ID.....");
                System.out.println("5.) DISPLAY ALL using Native SQL.....");
                System.out.println("6.) DISPLAY ALL using HQL.....");
                System.out.println("7.) EXIT.....");
                System.out.println("---------------------------------");
                System.out.print("Enter your Choice .:");
                int ch = Integer.parseInt(br.readLine());
                User u = new User();

                switch (ch) {
                    case 1:
                        t = s.beginTransaction();

                        System.out.print("Enter User ID .:");
                        int uid = Integer.parseInt(br.readLine());
                        System.out.print("Enter User Name .:");
                        String unm = br.readLine();
                        System.out.print("Enter Address .:");
                        String add = br.readLine();
                        System.out.print("Enter Phone No .:");
                        long phno = Long.parseLong(br.readLine());
                        System.out.print("Enter DOB (DD/MM/YYYY) .:");
                        String dob = br.readLine();

                        Date d = df.parse(dob);

                        u.setUid(uid);
                        u.setUnm(unm);
                        u.setAddress(add);
                        u.setPhno(phno);
                        u.setDob(d);

                        s.save(u);
                        t.commit();
                        System.out.println(".....Record Inserted.....");
                        break;
                    case 2:
                        t = s.beginTransaction();

                        System.out.print("Enter User ID for UPDATE .:");
                        int uid1 = Integer.parseInt(br.readLine());

                        u = (User) s.get(User.class, uid1);
                        if (u != null) {
                            System.out.print("Enter User Name .:");
                            String unm1 = br.readLine();
                            u.setUnm(unm1);
                            s.update(u);
                            System.out.println("......Record UPDATED SUCCESSFULLY......");

                        } else {
                            System.out.println("......Record NOT FOUND.....");
                        }
                        t.commit();
                        break;
                    case 3:
                        t = s.beginTransaction();

                        System.out.print("Enter User ID for DELETE .:");
                        int uid2 = Integer.parseInt(br.readLine());

                        u = (User) s.get(User.class, uid2);
                        if (u != null) {

                            s.delete(u);
                            System.out.println("......Record DELETED SUCCESSFULLY......");

                        } else {
                            System.out.println(".....Record NOT FOUND.....");
                        }
                        t.commit();
                        break;
                    case 4:
                        t = s.beginTransaction();

                        System.out.print("Enter User ID .:");
                        int uid3 = Integer.parseInt(br.readLine());

                        u = (User) s.get(User.class, uid3);
                        if (u != null) {
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.println("ID\tNAME\tAddress\tPhone No\tDOB");
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.print(u.getUid());
                            System.out.print("\t" + u.getUnm());
                            System.out.print("\t" + u.getAddress());
                            System.out.print("\t" + u.getPhno());
                            String dob1 = df.format(u.getDob());
                            System.out.println("\t" + dob1);
                            System.out.println("---------------------------------------------------------------------------");

                        } else {
                            System.out.println("Record NOT FOUND.....");
                        }
                        t.commit();
                        break;
                    case 5:
                        t = s.beginTransaction();

                        String sql = "SELECT * FROM tbl_user";
                        SQLQuery query = s.createSQLQuery(sql);
                        query.addEntity(User.class);    // Adding Entity Class
                        List<User> users = query.list();
                        System.out.println("------------------------------- Native SQL --------------------------------");
                        System.out.println("ID\tNAME\tAddress\tPhone No\tDOB");
                        System.out.println("---------------------------------------------------------------------------");
                        for (User u1 : users) {
                            System.out.print(u1.getUid());
                            System.out.print("\t" + u1.getUnm());
                            System.out.print("\t" + u1.getAddress());
                            System.out.print("\t" + u1.getPhno());
                            String dob1 = df.format(u1.getDob());
                            System.out.println("\t" + dob1);
                        }
                        System.out.println("---------------------------------------------------------------------------");
                        t.commit();

                        break;
                    case 6:
                        t = s.beginTransaction();

                        Query qry = s.createQuery("FROM User");
                        List<User> userl = qry.list();

                        System.out.println("-------------------------------- Using HQL --------------------------------");
                        System.out.println("ID\tNAME\tAddress\tPhone No\tDOB");
                        System.out.println("---------------------------------------------------------------------------");
                        for (User u1 : userl) {
                            System.out.print(u1.getUid());
                            System.out.print("\t" + u1.getUnm());
                            System.out.print("\t" + u1.getAddress());
                            System.out.print("\t" + u1.getPhno());
                            String dob1 = df.format(u1.getDob());
                            System.out.println("\t" + dob1);
                        }
                        System.out.println("---------------------------------------------------------------------------");
                        t.commit();
                        break;
                    case 7:
                        exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
