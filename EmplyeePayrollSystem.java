import java.util.ArrayList;
import java.util.Scanner;

// --- 1. Abstract Base Class (Parent) ---
// Abstract isliye kyunki hum generic "Employee" ka object nahi banayenge
abstract class Employee {
    private String name;
    private int id;
    private String designation;

    public Employee(String name, int id, String designation) {
        this.name = name;
        this.id = id;
        this.designation = designation;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public String getDesignation() { return designation; }

    // Har child class ko ye method apne hisaab se likhna padega
    public abstract double calculateSalary();

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Role: " + designation;
    }
}

// --- 2. Full Time Employee (Concrete Class) ---
class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, String designation, double monthlySalary) {
        super(name, id, designation);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        // Logic: Basic + HRA (20%) + DA (10%) - Tax (10%)
        double hra = monthlySalary * 0.20;
        double da = monthlySalary * 0.10;
        double gross = monthlySalary + hra + da;
        double tax = gross * 0.10; // 10% deduction
        return gross - tax;
    }
}

// --- 3. Part Time Employee (Concrete Class) ---
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int id, String designation, int hoursWorked, double hourlyRate) {
        super(name, id, designation);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        // No Tax/HRA for part-timers in this simple model
        return hoursWorked * hourlyRate;
    }
}

// --- 4. Payroll Manager (System Logic) ---
class PayrollManager {
    private ArrayList<Employee> employeeList;

    public PayrollManager() {
        employeeList = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        System.out.println("✅ Employee Added Successfully!");
    }

    public void removeEmployee(int id) {
        Employee toRemove = null;
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                toRemove = emp;
                break;
            }
        }
        if (toRemove != null) {
            employeeList.remove(toRemove);
            System.out.println("🗑 Employee Removed.");
        } else {
            System.out.println("❌ Employee not found!");
        }
    }

    public void displayAllEmployees() {
        System.out.println("\n--- 👥 Employee List ---");
        if (employeeList.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employeeList) {
                System.out.println(emp);
            }
        }
    }

    // --- 🖨 Generate Pay Slip ---
    public void generatePaySlip(int id) {
        Employee emp = null;
        for (Employee e : employeeList) {
            if (e.getId() == id) {
                emp = e;
                break;
            }
        }

        if (emp != null) {
            double netSalary = emp.calculateSalary();
            System.out.println("\n==================================");
            System.out.println("       🧾 SALARY PAY SLIP       ");
            System.out.println("==================================");
            System.out.println("Employee ID   : " + emp.getId());
            System.out.println("Name          : " + emp.getName());
            System.out.println("Designation   : " + emp.getDesignation());
            System.out.println("----------------------------------");
            if (emp instanceof FullTimeEmployee) {
                System.out.println("Type          : Full-Time (Tax/HRA Applied)");
            } else {
                System.out.println("Type          : Part-Time (Hourly)");
            }
            System.out.println("----------------------------------");
            System.out.printf("💰 NET SALARY : Rs. %.2f\n", netSalary);
            System.out.println("==================================\n");
        } else {
            System.out.println("❌ Employee ID not found.");
        }
    }
}

// --- 5. Main Class ---
public class EmplyeePayrollSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PayrollManager payroll = new PayrollManager();

        // Dummy Data for testing
      //  payroll.addEmployee(new FullTimeEmployee("Vikram", 101, "Manager", 50000));
      //  payroll.addEmployee(new PartTimeEmployee("Rahul", 102, "Intern", 40, 500));

        while (true) {
            System.out.println("\n=== 💼 EMPLOYEE PAYROLL SYSTEM ===");
            System.out.println("1. Add Full-Time Employee");
            System.out.println("2. Add Part-Time Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. List All Employees");
            System.out.println("5. Generate Pay Slip");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Name: "); String name1 = sc.next();
                    System.out.print("ID: "); int id1 = sc.nextInt();
                    System.out.print("Designation: "); String role1 = sc.next();
                    System.out.print("Monthly Salary: "); double sal = sc.nextDouble();
                    payroll.addEmployee(new FullTimeEmployee(name1, id1, role1, sal));
                    break;
                case 2:
                    System.out.print("Name: "); String name2 = sc.next();
                    System.out.print("ID: "); int id2 = sc.nextInt();
                    System.out.print("Designation: "); String role2 = sc.next();
                    System.out.print("Hours Worked: "); int hours = sc.nextInt();
                    System.out.print("Hourly Rate: "); double rate = sc.nextDouble();
                    payroll.addEmployee(new PartTimeEmployee(name2, id2, role2, hours, rate));
                    break;
                case 3:
                    System.out.print("Enter ID to Remove: ");
                    int remId = sc.nextInt();
                    payroll.removeEmployee(remId);
                    break;
                case 4:
                    payroll.displayAllEmployees();
                    break;
                case 5:
                    System.out.print("Enter Employee ID for Pay Slip: ");
                    int slipId = sc.nextInt();
                    payroll.generatePaySlip(slipId);
                    break;
                case 6:
                    System.out.println("Exiting System. Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}