
import java.util.Scanner;
import java.util.Date;

import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Project;

import com.ideas2it.training.services.employee.impl.EmployeeServicesImpl;
import com.ideas2it.training.services.employee.EmployeeServices;

import com.ideas2it.training.services.project.ProjectServices;
import com.ideas2it.training.services.project.impl.ProjectServicesImpl;

import com.ideas2it.training.services.leaveRecords.LeaveServices;
import com.ideas2it.training.services.leaveRecords.impl.LeaveServicesImpl;

import com.ideas2it.training.utils.Validiator;
import com.ideas2it.training.dao.employee.EmployeeDao;
import com.ideas2it.training.dao.leaveRecords.LeaveRecordsDao;
import com.ideas2it.training.dao.project.ProjectDao;

import com.ideas2it.training.dao.employee.impl.EmployeeDaoImpl;
import com.ideas2it.training.dao.leaveRecords.impl.LeaveRecordsDaoImpl;
import com.ideas2it.training.dao.project.impl.ProjectDaoImpl;
import com.ideas2it.training.constants.Gender;
import com.ideas2it.training.constants.EmployeeType;
import com.ideas2it.training.constants.LeaveType;

import com.ideas2it.training.view.employee.EmployeeView;

import java.util.ArrayList;
import java.util.List;

/**
* this MainController class is used to control the flow of data
* it takes user inputs and pass it to respective service layers for further operation.
* it gets results back from service layers, and those results are passed to view.
* @author : Akhter hussain dar
* @version : 1.0
* @since : 01-aug-2022
*/
public class MainController {
	
    private Scanner scanner = new Scanner(System.in);
    private Validiator validiator = new Validiator();
    private EmployeeServices employeeServices = new EmployeeServicesImpl();
    private LeaveServices LeaveServices = new LeaveServicesImpl();
    private ProjectServices ProjectServices = new ProjectServicesImpl();
        
    /**
    * main method calls init method for all CRUD operations
    */
    public static void main(String[] args) {
	MainController controller = new MainController();
	controller.init();
    }

    /**
    * init method calls crud opeation methods acc to user input
    */
    public void init() {
	
        final String CREATE = "1";
        final String READ = "2";
        final String UPDATE = "3";
        final String DELETE = "4";
        final String EXIT = "5";

	while(true) {
	    operationsMenu();
	    String choice = getString();	    
	    switch (choice) {
                case CREATE:
		    insertDetails();
	   	    continue;				
                case READ:
		    getDetails();
		    continue;
		case UPDATE:
		    updateDetails();
		    continue;    
                case DELETE:
		    deleteEmployee();
		    continue;
	        case EXIT:
		    break;    
	        default:
	            System.out.println("wrong input, please enter corect input");
		    continue;	     		
	    }
	    break;
	}
    }

    /**
    * insertDetails method is used to call add method of particular table
    */
    void insertDetails() {
        final String INSERT_EMPLOYEE_DETAILS = "1";
        final String INSERT_LEAVE_RECORDS = "2";
        final String INSERT_PROJECT_DETAILS = "3";
        final String INSERT_PREV_MENU = "4";
	while(true) {
	    insertMenu();
	    String choice = getString();
	    switch(choice) {
	        case INSERT_EMPLOYEE_DETAILS: 
					addEmployee();
					break;
		case INSERT_LEAVE_RECORDS:
					addLeaveRecords();
					continue;
		case INSERT_PROJECT_DETAILS:
					addProject();
					break;
		case "4":
					assignProjectToEmployee();
					break;
		case "5":
					break;
	    }
	    break;
        }
    }

    /**
    * addEmployee method takes user inputs and passes an object of employee to its service layer
    * it takes all valid inputs by calling getValid Methods.
    * status of the operation will be printed whether done or not with paricular message.
    */
    void addEmployee() {
        String employeeId = employeeServices.generateEmployeeId();
	System.out.println("Employee id assigned : "+employeeId);
        String employeeType = getValidEmployeeType().toString();
	String name = getValidName();
	String gender = getValidGender().toString();
	System.out.println(gender);
	String birthdate = getValidBirthdate();
	String designation = getValidDesignation();
	String contact = getValidContact();
	String email = getValidEmail();
	String probationTime = getValidProbationTime();
        boolean status = employeeServices.addEmployee(new Employee(employeeId,employeeType, name,gender,birthdate,designation,contact,email,probationTime));
	if (status)
	    System.out.println("Employee Added to database successfully");
	else
	    System.out.println("error while adding employee in database, Try Again");
    }

    /**
    * addLeaveRecords method takes user inputs and passes an object of LeaveRecords to its service layer
    * it takes all valid inputs by calling getValid Methods.
    * status of the operation will be printed whether done or not with paricular message.
    */
    void addLeaveRecords() {
	final int MAX_LEAVES_ALLOWED = 10;
	boolean status = false;
	String leaveType;
	LeaveRecords leaveRecords = new LeaveRecords();
	while (true) {
            String employeeId = getString("enter employee id");
	    Employee employee = employeeServices.getEmployee(employeeId);
	    if (employee != null) {
	    	employeeId = employee.getEmployeeId();
	        String fromDate;
	        String toDate;
		int leavesCount = LeaveServices.getLeaveCount(employeeId);
		if (leavesCount < MAX_LEAVES_ALLOWED) {
		    fromDate = getValidDate("Enter start date of leave: Format yyyy-mm-dd");
	            toDate = getValidDate("Enter end date of leave: Format yyyy-mm-dd");
	            if (validiator.isValidlLeaveDates(fromDate,toDate,leavesCount)) {
	   	        leaveType = getValidLeaveType().toString();
			leaveRecords = new LeaveRecords(fromDate,toDate,leaveType);
			status = LeaveServices.addLeaveRecords(employee.getEmployeeId(),leaveRecords);
			if (status)
	    		    System.out.println("Records Added to database successfully");
			else
	    		    System.out.println("error while adding records in database, Try Again");
			break;
		    } else {
			System.out.println("you have only "+(MAX_LEAVES_ALLOWED-leavesCount)+" left, please enter valid dates");
			break;
	            }
		    
		}
		else {
		    System.out.println("Sorry you have no leaves left");
		    break;
		}
	    } else {
	        System.out.println("employee with this id does not exist");
	        continue;
	    }
	}

    }

    /**
    * addProject method takes user inputs and passes an object of Project to its service layer
    * it takes all valid inputs by calling getValid Methods.
    * status of the operation will be printed whether done or not with paricular message.
    */
    void addProject() {
	Project project;
	while (true) {

		String projectManagerId = getString("enter the project Manager's Employee ID");
		Employee manager = employeeServices.getEmployee(projectManagerId);
		if (manager != null) {
		
		    String projectName = getString("enter the Project");
	            String startDate = getValidDate("Enter start date of project: Format yyyy-mm-dd");
		    String description =getString("enter project description");
		    String managerName = manager.getName();
		    String clientName = getString("enter Client Name");

		    project = new Project(projectName,startDate,description,managerName,clientName);
	            break;
	        } else {
	            System.out.println("manager with this id does not exist");
	            continue;
	   	}    
	    
	}
        boolean status = ProjectServices.addProject(project);
	if (status)
	    System.out.println("project Added to database successfully");
	else
	    System.out.println("error while adding project in database, Try Again");
    }

    void assignProjectToEmployee() {
	boolean status = false;
	while(true) {
	    String userEmployeeId = getString("enter employee's ID");
	    Employee employee = employeeServices.getEmployee(userEmployeeId);
	    if (employee!=null) {
	        List<Project> projects = ProjectServices.getProjects();
	        viewProjects(projects);
	        int projectId = getInt("enter project ID");
	        Project project = ProjectServices.getProject(projectId);
		if(project != null) {
		    System.out.println(employee.getEmployeeId());
		    status = employeeServices.assignProject(employee.getEmployeeId(),project.getProjectId());
		    if (status)
			System.out.println("project assigned");
	            else
			System.out.println("project could not be assigned , try again");
		    break;	
		} else {
		    System.out.println("not a valid project id");
		    continue;
	    	}
	    } else {
		System.out.println("not a valid Employee id");
		continue;
	    }
	}
    }


    /**
    * getDetails method calls get particular model methodto get details from database via service layer.
    * 
    */
    void getDetails() {
        final String ALL_EMPLOYEES = "1";
        final String EMPLOYEE = "2";
        final String LEAVE_RECORDS = "3";
        final String LEAVE_RECORD = "4";
        final String PROJECTS = "5";
        final String PROJECT = "6";
        final String PREVIOUS_MENU = "7";

        while (true) {
	    EmployeeView employeeView = new EmployeeView();
	    readMenu();
	    String choice = getString();
	    switch(choice) {
		case ALL_EMPLOYEES :
				viewEmployees(employeeServices.getEmployees());

		    		continue;
		case EMPLOYEE:	  
		    		getEmployee();
	 	    		continue;
	        case LEAVE_RECORDS:
		    		viewLeaveRecords(LeaveServices.getLeaveRecords());
		    		continue;
	        case LEAVE_RECORD:
		    		getLeaveRecord();
		    		continue;
	        case PROJECTS:
				viewProjects(ProjectServices.getProjects());
		    		continue;
	        case PROJECT:
				getProjectById();
		    		continue;
	        case PREVIOUS_MENU:
		    		break;
	        default:
 		    System.out.println("invalid input");
	 	    continue;
	    }
	    break;
	}
    }

    /**
    * getEmployee methods retrieves details of Particular employeeId from database and passes to view to print those details.
    * 
    */
    void getEmployee() {
	EmployeeView employeeView = new EmployeeView();
	String employeeId = getString("enter employee id");		
	Employee result = employeeServices.getEmployee(employeeId.toUpperCase());
	if (result == null) 
	    System.out.println("Empolyee not found");	
	else {
	    employeeView.viewEmployee(result);
	    System.out.println(result.getLeaves());	
	    viewProjects(result.getProjects());
	    //2viewLeaveRecords(result.getLeaves());
	}
    }

    /**
    * getLeaveRecord methods retrieves details of Particular employeeId from database and passes to its view to print those details.
    * 
    */
    void getLeaveRecord() {
	final int MAX_LEAVES_ALLOWED = 10;
	String employeeId = getString("enter employee id");		
	List<LeaveRecords> leaveRecords = LeaveServices.getLeaveRecord(employeeId.toUpperCase()); 
	if (leaveRecords.size() == 0) 
	    System.out.println("No records for this employee ID in Leaves");	
	else {
	    viewLeaveRecords(leaveRecords);
	    int leaveCount = LeaveServices.getLeaveCount(employeeId);
	    System.out.println("you have used "+ leaveCount+" leaves do far\n"
					+"you have "+(MAX_LEAVES_ALLOWED-leaveCount)+" leaves left");
	    }
    }

    /**
    * getproject methods retrieves details of Particular employeeId from database and passes to its view to print those details.
    * 
    */
    void getProjectById() {
	String employeeId = getString("enter employee id");		
	List<Project> projects = ProjectServices.getProjects(); 
	if (projects == null) 
	    System.out.println("No records for this employee ID in Projects table");	
	else
	    viewProjects(projects);
    }

    /**
    * updateDetails method takes user input for update field
    * Based on that input , update method is callled to update that model.
    */
    void updateDetails() {
	final String EMPLOYEE = "1";
	final String LEAVE_RECORD = "2";
	final String PROJECT = "3";
	final String PREVIOUS_MENU = "4";
        while(true) {
	    updateMenu();
	    String choice = getString();

	    switch (choice) {
		case EMPLOYEE :
				updateEmployee();
				continue;
		case LEAVE_RECORD:
				updateLeaveRecord();
				continue;
		case PROJECT:
				updateProject();
				continue;
		case PREVIOUS_MENU:
				break;
	    }
	    break;
	}
	 
    }

    /**
    * updateEmployee updates particular field of Employee.
    * prints status if true , if not then reason is printed.
    */

    void updateEmployee() {

    	final String UPDATE_EMPLOYEE_TYPE = "1";
    	final String UPDATE_NAME = "2";
    	final String UPDATE_BIRTHDATE = "3";
    	final String UPDATE_DESIGNATION = "4";
    	final String UPDATE_CONTACT = "5";
    	final String UPDATE_EMAIL = "6";
    	final String UPDATE_PROBATION_TIME = "7";
    	final String SUBMIT_UPDATES = "8";
    	final String UPDATE_PREVIOUS_MENU = "9";
	
	String userEmployeeId = getString("Enter Employee Id");
	Employee employee = employeeServices.getEmployee(userEmployeeId);
	if (employee == null) {
	    System.out.println("EmployeeId not found in employees");
	    return;
	} else {
	    while(true) {
	        updateEmployeeMenu();
	        String choice = getString();
                switch(choice) {
		    case UPDATE_EMPLOYEE_TYPE:
	    	        String newEmployeeType = getValidEmployeeType().toString();
			employee.setEmployeeType(newEmployeeType);
		        //employeeServices.updateEmployeeType(employee);    			        
		        continue;
		    case UPDATE_NAME:
	    	        String newName = getValidName();
			employee.setName(newName);
		        //employeeServices.updateName(employee);    			        
		        continue;
		    case UPDATE_BIRTHDATE:
	    	        String newBirthdate = getValidBirthdate();
			employee.setBirthdate(newBirthdate);
		        //employeeServices.updateBirthdate(employee);
		        continue;
		    case UPDATE_DESIGNATION:
	    	        String newDesignation = getValidDesignation();
			employee.setDesignation(newDesignation);
			//employeeServices.updateDesignation(employee);
		    	continue;
		    case UPDATE_CONTACT:
	    	        String newContact = getValidContact();
			employee.setContact(newContact);
			//employeeServices.updateContact(employee);
		    	continue;
		    case UPDATE_EMAIL:
	    	        String newEmail = getValidEmail();
			employee.setEmail(newEmail);
	   	        //employeeServices.updateEmail(employee);
		    	continue;
		    case UPDATE_PROBATION_TIME:
	    	        String newProbationTime = getValidProbationTime();
			employee.setProbationTime(newProbationTime);
		        //employeeServices.updateProbationTime(employee);
		        continue;
		    case SUBMIT_UPDATES:
		        employeeServices.updateEmployee(employee);
		        break;
		    case UPDATE_PREVIOUS_MENU:
		        break;
		    default:
		        System.out.println("invalid option");
		        continue;
                }
	        break;
            }   	
	}   	   
    }

    /**
    * updateLeaveRecord leave type field only.
    * prints status if true , if not then reason is printed.
    */
    void updateLeaveRecord() {
	String message = "";
	String employeeId = getString("Enter Employee Id");
	List<LeaveRecords> leaveRecords = LeaveServices.getLeaveRecord(employeeId.toUpperCase());
	if (leaveRecords.size() > 0) {
	    viewLeaveRecords(leaveRecords);
	    int leaveId = getInt("enter leaveId");	
	    for(LeaveRecords leaveRecord:leaveRecords) {
                if (leaveRecord.getLeaveId() == leaveId) {
	    	    String leaveType = getValidLeaveType().toString();
	    	    leaveRecord.setLeaveType(leaveType);
	            boolean status = LeaveServices.updateLeaveRecord(employeeId,leaveRecord);
	            if (status) {
		        message = "updation completed";
			break;
	    	    } else 
			message = "updation failed due some reason! please try again";
		    break;
		} else 
		    message = "invalid leave id ";
	    }
	    System.out.println(message);
        } else {
	    System.out.println("Employee Not Found! try with some other employee Id");
        }
    }

    /**
    * updateProject() updates employeedId field only.
    * prints status if true , if not then reason is printed.
    */
    void updateProject() {
	String message = "";
	String employeeId = getString("Enter Employee Id");
	List<Project> projects = ProjectServices.getProjects();
	if (projects.size() > 0) {
	    viewProjects(projects);
	    for(Project project:projects) {
		int projectId = getInt("enter project Id");
			
                if (project.getProjectId() == projectId) {
	    	    String newEmployeeId = getString("enter new Employee Id");
		    Employee employee = employeeServices.getEmployee(newEmployeeId);
		    if(employee != null) {
	    	        project.setEmployeeId(employee.getEmployeeId());
	                boolean status = ProjectServices.updateProject(project);
	                if (status) {
		            message = "updation completed";
			    List<Project> updatedProject = new ArrayList<>();
			    updatedProject.add(project);
			    viewProjects(updatedProject);
			    break;
	    	        } else 
			    message = "updation failed due some reason! please try again";
		    } else 
			message = " new employee not registered";
		} else 
		    message = "invalid project id ";
	    }
	    System.out.println(message);
        } else {
	    System.out.println("Employee Not Found! try with some other employee Id");
        }
    }


    /**
    * deleteEmployee() takes employeeId from user which is to be deleted.
    * if employee id is valid, its object is passed to service layer for deletion.
    */
    void deleteEmployee() {
	String userEmployeeId = getString("enter employee Id");
	Employee employee = employeeServices.getEmployee(userEmployeeId);
	if (employee == null) {
	    System.out.println("Employee not found");
	} else {
	    boolean status = employeeServices.deleteEmployee(employee);
	    if (status == true)
		System.out.println("employee deleted");
	    else
		System.out.println("employee was not deleted , please try again");
	}

    }

//*************************************************************************************************************

    /**
    * getValidName() take user input name and validates by calling utils.Validiator. 
    * if valid then returns the value.
    * @return : returns String type value which is a valid name.
    */
    public String getValidName() {
	while (true) {
	    String name = getString("Enter Name of an Employee");
	    if (validiator.validiateName(name) == true) 
		return name;
	    else
		System.out.println("name entered is invalid");
	}
    }

    /**
    * getValidBirthdate() take user input birthdate as String and validates by calling utils.Validiator. 
    * if valid then returns the value.
    * @return : returns String type value which is a valid bithdate .
    */
    public String getValidBirthdate() {
	while (true) {
            String birthdate = getString(" enter birthdate in format YYYY-MM-DD : ");
	    if (validiator.validiateBirthdate(birthdate)) 
		return birthdate;
	    else{
                continue;
	    }
	}
    }

    /**
    * getValidDesignation() take user input Designation and validates by calling utils.Validiator. 
    * if valid then returns the value.
    * @return : returns String type value which is a valid Designation.
    */
    public String getValidDesignation() {
	while (true) {
	    String designation = getString(" enter designation  : ");

	    if (validiator.validiateName(designation) == true) 
		return designation;
	    else
		System.out.println("name entered is invalid");
	}
    }

    /**
    * getValidEmployeeType take user input Integer and validates by calling enum class EmployeeType. 
    * if valid then returns the value.
    * @return : returns EmployeeType type value which is a valid enum.
    */
    public EmployeeType getValidEmployeeType() {
	while(true) {
	    try {
		System.out.println("enter Employee type: ");
		System.out.println(" 1.Trainer\n 2.Trainee ");
		int value = Integer.parseInt(scanner.nextLine());
	
		EmployeeType EType = EmployeeType.getEmployeeType(value);
		if ( EType != null)
	    	    return EType;
		else
		    System.out.println("enter valid option");
	    } catch (Exception e) {
	        System.out.println("enter integer type Value");
	   
	    }

	}
    }

    /**
    * getValidGender() take user input Integer and validates by calling its enum class . 
    * if valid then returns the value.
    * @return : returns Gender type value which is a valid enum.
    */
    public Gender getValidGender() {
	while(true) {
	    try {
		System.out.println("enter gender of enployee");
		System.out.println(" 1.MALE\n 2.FEMALE\n 3.OTHER");
		int value = Integer.parseInt(scanner.nextLine());
		Gender gender = Gender.getGender(value);
		if ( gender != null)
	    	    return gender;
	    } catch (Exception e) {
	        System.out.println("enter integer type Value");
	    	continue;
	    }

	}
    }

    /**
    * getValidContact() take user input String and validates by calling validiator. 
    * if valid then returns the value.
    * @return : returns String type value which is a valid contact.
    */
    public String getValidContact() {
	while (true) {
	    System.out.print(" enter contact : ");
	    String contact = scanner.nextLine();
	    if (validiator.validiateContact(contact) == true) 
		return contact;
	    else
		System.out.println("contact entered is invalid");
	    continue;
	}
    }

    /**
    * getValidEmail() take user input String and validates by calling validiator. 
    * if valid then returns the value.
    * @return : returns String type value which is a valid Email.
    */
    public String getValidEmail() {
	while (true) {
	    System.out.print(" enter email : ");
            String email = scanner.nextLine();
	    if (validiator.validiateEmail(email)) 
		return email;
	    else
	        System.out.println("email entered is invalid");
	    continue;
	}
    }

    /**
    * getValidProbationTime() take user input String and validates by calling validiator. 
    * if valid then returns the value.
    * @return : returns String type value.
    */
    public String getValidProbationTime() {
	while (true) {
	    System.out.print(" enter probationTime  : ");
	    String probationTime = scanner.nextLine();
	    if (validiator.validiateName(probationTime) == true) 
		return probationTime;
	    else
	        System.out.println("probationTime entered is invalid,");
	    continue;
	}
    }

    /**
    * getValidLeaveType() take user input Integer and validates by calling its enum class . 
    * if valid then returns the value.
    * @return : returns LeaveType type value which is a valid enum.
    */
    public LeaveType getValidLeaveType() {
	while(true) {
	    try {
		System.out.println("enter gender of enployee");
		System.out.println(" 1.Casual\n 2.Sick\n 3.Medical");
		int value = Integer.parseInt(scanner.nextLine());
		LeaveType leaveType = LeaveType.getLeaveType(value);
		if ( leaveType != null)
	    	    return leaveType;
	    } catch (Exception e) {
	        System.out.println("enter integer type Value");
	    	continue;
	    }

	}
    }
    /**
    * getValidDate() take user input String and validates by calling utils.Validiator . 
    * if valid then returns the value.
    * @return : returns String type value which is a valid Date.
    */
    public String getValidDate(String message) {
	while (true) {
	    String dateStr = getString(message);
    	    if (validiator.isValidDate(dateStr)) {
	    	return dateStr;
	    } else {
                System.out.println("enter valid date");
	        continue;
	    }
	}
    }
	    

//**********************************************************************************************

    /* Trainee Menu */
    public void operationsMenu() {
	System.out.println("***************OPERATIONS*************** \n"
				+"1.ADD-------------------------------- \n"
				+"2.READ ------------------------------ \n"
				+"3.UPDATE ---------------------------- \n"
				+"4.REMOVE ---------------------------- \n"
				+"5.EXIT--------- --------------------- \n"
				+"************************************* \n"
				+"Please enter your input : ");
    }

    /* Read Menu */
    public void readMenu() {
	System.out.println("***********************************\n"
			+"1.ALL EMPLOYEE DETAILS \n"
			+"2.INDIVISUAL EMPLOYEE DETAILS \n"
			+"3.ALL LEAVE RECORDS\n"
			+"4.LEAVE RECORD OF INDIVISUAL EMPLOYEE\n"
			+"5.PROJECTS\n"
			+"6.PROJECTS BY EMPLOYEE ID \n"
			+"7.PREVIOUS MENU \n"
			+"************************************* \n"
			+"Please enter your input : ");
    }

    public void insertMenu() {
	System.out.println("************************************* \n"
				+"1.INSERT EMPLOYEE DETAILS \n"
				+"2.INSERT LEAVE RECORD \n"
				+"3.INSERT PROJECT DETAILS \n"
				+"4.PREVIOUS MENU \n"
				+"************************************* \n"
				+"Please enter your input : ");
    }

    /* update menu */
    public void updateMenu() {
	System.out.println("***********************************\n"
			+"1.Employees \n"
			+"2.LeaveRecords \n"
			+"3.Projects\n"
			+"4.PREVIOUS MENU \n"
			+"************************************* \n"
			+"Please enter your input : ");
    }



    public String getString(String message) {
	Scanner scannerString = new Scanner(System.in);
	System.out.println(message);
	String str = scannerString.nextLine();
	return str;
    }

    public String getString() {
	Scanner scannerString = new Scanner(System.in);
	String str = scannerString.nextLine();
	return str;
    }
    public int getInt(String message) {
        while(true) {
            try {
	        Scanner scannerInt = new Scanner(System.in);
	        System.out.println(message);
	        int intData = scannerInt.nextInt();
	        return intData;
	    } catch (Exception e) {
	        System.out.println("enter integer value");
            }
        }
    }





    public void updateEmployeeMenu() {
	System.out.println("*************************************\n"
	+"\n1.EMPLOYEE TYPE"
	+"\n2.UPDATE NAME"
	+"\n3.UPDATE DIRTHDATE"
	+"\n4.UPDATE DESIGNATION"
	+"\n5.UPDATE CONTACT"
	+"\n6.UPDATE EMAIL"
        +"\n7.PROBATION TIME"
	+"\n8.SAVE UPDATES AND EXIT "
	+"\n8.EXIT WITHOUT SAVING "
	+"\n*************************************"
	+"\nENTER ANY OPTION");

	
    }


    public void viewEmployees(List<Employee> employees) {
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
        System.out.printf("| %-8s | %-7s | %-23s | %-6s |","ID","TYPE","NAME","GENDER");
        System.out.printf(" %-10s | %-3s | %-14s |","DOB","AGE","DESIGNATION");
        System.out.printf(" %-12s | %-20s |","CONTACT","EMAIL");
        System.out.printf(" %-12s | \n","PROBATION");
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
        for(Employee employee:employees) {  
            System.out.printf("| %-8s ", employee.getEmployeeId());
            System.out.printf("| %-7s ", employee.getEmployeeType());
            System.out.printf("| %-23s ", employee.getName());
            System.out.printf("| %-6s ", employee.getGender());
            System.out.printf("| %-10s ", employee.getBirthdate());
            System.out.printf("| %-3s ", employee.getAge(employee.getBirthdate()));
            System.out.printf("| %-14s ", employee.getDesignation());
            System.out.printf("| %-12s ", employee.getContact());
            System.out.printf("| %-20s ", employee.getEmail());
            System.out.printf("| %-12s |", employee.getProbationTime());
	    System.out.printf("\n");

        }
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");  
    }

    public void viewEmployee(Employee employee) {
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
        System.out.printf("| %-8s | %-7s | %-23s | %-6s |","ID","TYPE","NAME","GENDER");
        System.out.printf(" %-10s | %-3s | %-14s |","DOB","AGE","DESIGNATION");
        System.out.printf(" %-12s | %-20s |","CONTACT","EMAIL");
        System.out.printf(" %-12s | \n","PROBATION");
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");
          
        System.out.printf("| %-8s ", employee.getEmployeeId());
        System.out.printf("| %-7s ", employee.getEmployeeType());
        System.out.printf("| %-23s ", employee.getName());
        System.out.printf("| %-6s ", employee.getGender());
        System.out.printf("| %-10s ", employee.getBirthdate());
        System.out.printf("| %-3s ", employee.getAge(employee.getBirthdate()));
        System.out.printf("| %-14s ", employee.getDesignation());
        System.out.printf("| %-12s ", employee.getContact());
        System.out.printf("| %-20s ", employee.getEmail());
        System.out.printf("| %-12s |", employee.getProbationTime());
	System.out.printf("\n");

    
        System.out.print("+----------+---------+-------------------------+--------+");
        System.out.print("------------+-----+----------------+");
        System.out.print("--------------+----------------------+--------------+\n");  
    }


    public void viewLeaveRecords(List<LeaveRecords> leaveRecords) {
        System.out.print("+------+----------+------------+------------+--------------+\n");
        System.out.printf("| %-4s | %-8s | %-10s | %-10s | %-12s |\n","L_id","e_id","F_date","To_date","type");
        System.out.print("+------+----------+------------+------------+--------------+\n");
        for(LeaveRecords record: leaveRecords) {  
            System.out.printf("| %-4s ", record.getLeaveId());
            System.out.printf("| %-8s ", record.getEmployeeId());
            System.out.printf("| %-10s ", record.getFromDate());
            System.out.printf("| %-10s ", record.getToDate());
            System.out.printf("| %-12s |", record.getLeaveType());
	    System.out.printf("\n");
        }
        System.out.print("+------+----------+------------+------------+--------------+\n"); 
    }


    public void viewProjects(List<Project> projects) {
        System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
        System.out.printf("| %-6s | %-20s | %-12s | %-26s | %-15s | %-15s |\n","p_id","name","start_date","description","manager","client");
        System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
        for(Project record: projects) {  
            System.out.printf("| %-6s ", record.getProjectId());
            System.out.printf("| %-20s ", record.getName());
            System.out.printf("| %-12s ", record.getStartDate());
            System.out.printf("| %-26s ", record.getDescription());
            System.out.printf("| %-15s ", record.getProjectManager());
            System.out.printf("| %-15s |", record.getClientName());
	    System.out.printf("\n");
        }
        System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
    }

}