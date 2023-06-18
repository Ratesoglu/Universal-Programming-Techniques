package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Main {
    static final double PREDICATE_QUERY = 2564;
    static final int CURRENT_YEAR = 2020;
    static final double MODIFY_BONUS = 0.6;

    public static void main(String[] args) {
        Manager manager1 = new Manager("Ertan", 9500, 1991,null);
        Manager manager2 = new Manager("Sabriye", 5000, 1992, manager1);
        Manager manager3 = new Manager("Werner", 12000, 1993, null);
        List<Employee> employeeList = new ArrayList<>(List.of(manager1,manager2,manager3,

        // -> Highest salary with-out bonus.

        new Trainee("Durant", 2300, 1990, manager1, 0.1),
                 new RegWorker("Kevin", 2200, 1995, manager1, 0.2),
                new Trainee("Lucas", 2000, 1998, manager2, 0.3),
                new RegWorker("Eren", 2500, 1997, manager3, 0.1),
                new RegWorker("Lebron", 3000, 1996, manager3, 0.2)
               ));
        employeeList.add(manager1);
        employeeList.add(manager2);
        employeeList.add(manager3);

        System.out.println("\n\n -- Payroll for all employees.");
        System.out.println(payrollAllEmployees(employeeList));
        System.out.println("\n\n -- Payroll of subordinates of given manager.");
        System.out.println(payrollSubsOfManager(manager1));
        System.out.println("\n\n -- Total cost of all bonuses.");
        System.out.println(totalCostOfBonuses(employeeList));
        System.out.println("\n\n -- Employee with the longest experience.");
        System.out.println(longestExperience(employeeList));
        System.out.println("\n\n -- Highest salary with-out bonuses.");
        System.out.println(highestSalWithOutBonus(employeeList));
        System.out.println("\n\n -- Highest salary with bonuses.");
        System.out.println(highestSalWithBonus(employeeList));
        // List all employees that have more salary than given value
        System.out.println("\n\n -- Employees that have more than '" + PREDICATE_QUERY + "' unit salary. (It is modifiable)");
        System.out.println(salaryFilter(employeeList, PREDICATE_QUERY));
        System.out.println("\n\n -- Modify bonuses for all regular workers whose names starts with a letter from the" +
                " second half of the alphabet and who are subordinates of a given manager.");
        System.out.println(modifyBonuses(manager3, MODIFY_BONUS));
    }
    private static List<Employee> modifyBonuses(Manager manager, double modifyBonus) {
        return manager.getSubs()
                .stream()
                .filter(e -> e instanceof RegWorker)
                .filter(employee -> employee.getNameOfEmpl().indexOf(0) <= 'm')
                .peek(employee -> ((RegWorker) employee).setBonus(modifyBonus)).collect(Collectors.toList());
    }
    public static Double highestSalWithBonus(List<Employee> employeeList) {
        return employeeList
                .stream()
                .filter(e -> e instanceof Worker)
                .map(e -> (Worker)e)
                .map(e -> e.getSalary() + (e.getSalary() * e.getBonus()))
                .reduce(0.0, Double::max);
    }
    public static Double totalCostOfBonuses(List<Employee> employeeList) {
        return employeeList
                .stream()
                .filter(e -> e instanceof Worker)
                .map(e -> (Worker)e)
                .map(e -> e.getSalary() * e.getBonus())
                .reduce(0.0, Double::sum);
    }
    public static Double highestSalWithOutBonus(List<Employee> employeeList) {
        return employeeList
                .stream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::max);
    }

    public static Employee longestExperience(List<Employee> employeeList) {
        return employeeList
                .stream()
                .min(Comparator.comparing(Employee::getyOfEmpl))
                .orElseThrow(NoSuchElementException::new);
    }
    public static List<Employee> salaryFilter(List<Employee> employeeList, double givenSalary) {
        return employeeList.stream()
                .filter(employee -> employee.getSalary() > givenSalary)
                .collect(Collectors.toList());
    }
    public static List<Payroll> payrollSubsOfManager(Manager manager) {
        System.out.println("Manager: " + manager.getNameOfEmpl() );
        return manager.getSubs()
                .stream()
                .map(Payroll::new)
                .collect(Collectors.toList());
    }
    public static List<Payroll> payrollAllEmployees(List<Employee> list) {
        return  list.stream()
                .map(Payroll::new)
                .collect(Collectors.toList());
    }


    public static abstract class Employee {

        String nameOfEmpl;
        int yOfEmpl ;
        double salary;
        Manager boss;
        List<Employee> subs;
        public Employee(String nameOfEmpl, double salary, int yOfEmpl, Manager boss) {
            this.nameOfEmpl = nameOfEmpl;
            this.yOfEmpl = yOfEmpl;
            this.salary = salary;
            subs = new ArrayList<>();
            if(boss != null) {
                this.boss = boss;
                boss.subs.add(this);
            }
        }
        public String getNameOfEmpl() {
            return nameOfEmpl;
        }
        public int getyOfEmpl() {
            return yOfEmpl;
        }
        public double getSalary() {
            return salary;
        }
        public List<Employee> getSubs() {
            return subs;
        }

    }
    public static class Manager extends Employee {
        public Manager(String name, double salary, int yOfEmpl, Manager boss) {
            super(name, salary, yOfEmpl, boss);
        }
        @Override
        public String getNameOfEmpl() {
            return super.getNameOfEmpl();
        }
        @Override
        public int getyOfEmpl() {
            return super.getyOfEmpl();
        }
        @Override
        public double getSalary() {
            return super.getSalary();
        }
        @Override
        public String toString() {
            return "MANAGER: " + nameOfEmpl +
                    ", salary = " + salary +
                    ", experience = " + (CURRENT_YEAR - getyOfEmpl()) + " year\n" ;
        }
        @Override
        public List<Employee> getSubs() {
            return super.getSubs();
        }
    }
    public abstract static class Worker extends Employee {
        double bonus;
        public Worker(String nameE, double salary, int yOfEmpl, Manager boss, double bonus) {
            super(nameE, salary, yOfEmpl, boss);
            this.bonus = bonus;
        }
        @Override
        public String getNameOfEmpl() {
            return super.getNameOfEmpl();
        }
        @Override
        public int getyOfEmpl() {
            return super.getyOfEmpl();
        }
        @Override
        public double getSalary() {
            return super.getSalary();
        }
        abstract double getBonus();
        abstract void setBonus(double modifyBonus);
    }
    public static class RegWorker extends Worker{
        public RegWorker(String nameOfEmpl, double salary, int yOfEmpl, Manager boss, double bonus) {
            super(nameOfEmpl,  salary, yOfEmpl, boss, bonus);
        }
        @Override
        public String toString() {
            return "Regular Worker : " + nameOfEmpl
                    + "', Year of Employment=" + yOfEmpl +
                    ", salary=" + salary +
                    ", boss=" + boss.getNameOfEmpl() +
                    ", bonus= " + bonus + "\n";
        }
        @Override
        public String getNameOfEmpl() {
            return super.getNameOfEmpl();
        }
        @Override
        public int getyOfEmpl() {
            return super.getyOfEmpl();
        }
        @Override
        public double getSalary() {
            return super.getSalary();
        }
        @Override
        double getBonus() {
            return super.bonus;
        }
        @Override
        void setBonus(double modifyBonus) {
            super.bonus = modifyBonus;
        }
    }
    public static class Trainee extends Worker{

        public Trainee(String nameOfEmpl, double salary, int yOfEmpl, Manager boss, double bonus) {
            super(nameOfEmpl,  salary, yOfEmpl, boss, bonus);
        }
        @Override
        public String toString() {
            return "TRAINEE: " + nameOfEmpl +
                    ", Year of Employment =" + yOfEmpl
                    + ", salary =" + salary +
                    ", manager =" + boss.getNameOfEmpl() +
                    ", bonus = " + bonus + "\n";
        }
        @Override
        public String getNameOfEmpl() {
            return super.getNameOfEmpl();
        }
        @Override
        public int getyOfEmpl() {
            return super.getyOfEmpl();
        }
        @Override
        public double getSalary() {
            return super.getSalary();
        }
        @Override
        double getBonus() {
            return super.bonus;
        }
        @Override
        void setBonus(double modifyBonus) {
            super.bonus = modifyBonus;
        }
    }

    private static class Payroll {
        Employee employee;
        String empClass;
        public Payroll(Employee employee) {
            this.employee = employee;
            empClass = employee.getClass().getName().substring(getClass().getName().indexOf("$") + 1, getClass().getName().length());
        }
        @Override
        public String toString() {
            return "Payroll{ Employee " + empClass + ": " + employee.getNameOfEmpl() + ", Salary= " + employee.getSalary()+"}\n";
        }
    }
}
