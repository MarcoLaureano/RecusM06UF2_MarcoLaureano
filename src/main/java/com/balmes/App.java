package com.balmes;

import com.balmes.modelo.Departments;
import com.balmes.modelo.Employees;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory;
        sessionFactory = configuration.buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            System.out.println("===========================================\n"+
                                "MANTENIMENT EMPLEATS\n" +
                                "===========================================");
            System.out.println("Escolliu quina acció voleu fer:");
            System.out.println("1. Consultar empleats");
            System.out.println("2. Inserir nou empleat");

            int option = Integer.parseInt(readInput());

            if (option == 1) {
                System.out.println("===========================================\n" +
                                    "CONSULTA EMPLEATS\n" +
                                    "===========================================");
                System.out.println("Introduir el nom de departament:");
                String departmentName = readInput();

                Departments department = getDepartmentoNombre(session, departmentName);
                if (department != null) {
                    List<Employees> employees = getEmployeesByDepartment(session, department.getDeptno());

                    System.out.println("Emp_no\tNom\tCognoms\tData_naix\tRol\tSalari");
                    System.out.println("======\t===\t======\t========\t===\t=====");
                    for (Employees employee : employees) {
                        System.out.println(employee.getEmp_no() + "\t" + employee.getLast_name()  +
                                "\t" + employee.getFirst_name() + "\t" + employee.getBirth_date() + "\t" +
                                employee.getRole() + "\t" + employee.getSalary());
                    }
                } else {
                    System.out.println("El departamento no existe.");
                }
            } else if (option == 2) {
                System.out.println("===========================================\n"+
                                    "NOU EMPLEAT\n" +
                                    "===========================================\n");
                System.out.println("Cognom:");
                String apellido = readInput();
                System.out.println("Nom:");
                String nombre = readInput();
                System.out.println("Data de naixement (yyyy-MM-dd):");
                Date dataNacido = parseDate(readInput());
                System.out.println("Gènere:");
                String genero = readInput();
                System.out.println("Data d'incorporació (yyyy-MM-dd):");
                Date contrato = parseDate(readInput());
                System.out.println("Salari:");
                double salario = Double.parseDouble(readInput());
                System.out.println("Rol:");
                String rol = readInput();
                System.out.println("Departament:");
                int departmentNumber = Integer.parseInt(readInput());

                Departments departmento = getDepartmentByNumber(session, departmentNumber);
                if (departmento != null) {
                    Employees newEmployee = new Employees(apellido, nombre, dataNacido, genero, contrato, rol, salario, departmento);
                    session.beginTransaction();
                    session.save(newEmployee);
                    session.getTransaction().commit();
                    System.out.println("El nuevo empleado ha sido insertado correctamente.");
                } else {
                    System.out.println("El departamento no existe.");
                }
            } else {
                System.out.println("Solamente 1 o 2.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sessionFactory.close();
        }
    }

    private static String readInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Departments getDepartmentoNombre(Session session, String nomDepartament) {
        String hql = "FROM Departments WHERE deptname = :name";

        return session.createQuery(hql, Departments.class)
                .setParameter("name", nomDepartament)
                .uniqueResult();
    }

    private static Departments getDepartmentByNumber(Session session, int numeroDepartamento) {
        String hql = "FROM Departments WHERE deptno = :deptNo";
        return session.createQuery(hql, Departments.class)
                .setParameter("deptNo", numeroDepartamento)
                .uniqueResult();
    }

    private static List<Employees> getEmployeesByDepartment(Session session, int departmentNumber) {
        String hql = "FROM Employees WHERE department.deptno = :deptNo";
        return session.createQuery(hql, Employees.class)
                .setParameter("deptNo", departmentNumber)
                .getResultList();
    }
}
