# Design Document Template

Authors: Angione Francesco, Butera Alberto, Di Fabio Matteo, Forese Leonardo

Date: 16/04/2019

Version: 1

# Contents

- [Package diagram](#package-diagram)
- [Class diagram](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)


# Package diagram
```plantuml
left to right direction

package GUI as UserInterface
package "LaTazza Desktop Application"  as Application
package Data as Data
package Exceptions 
 
 
UserInterface --> Application  
Data --> Application
Exceptions --> Data
```


# Class diagram

```plantuml
left to right direction
class GUI{
} 
note top of GUI :It is already provided

class LaTazza {
void main()
}
note bottom of LaTazza: It simple provides functions to the GUI
Interface DataInterface{   
public Integer sellCapsules()
  public void sellCapsulesToVisitor()
  public Integer rechargeAccount()
  public void buyBoxes()
  public List<String> getEmployeeReport()
  public List<String> getReport()
  public Integer createBeverage()
  public void updateBeverage()
  public String getBeverageName()
  public Integer getBeverageCapsulesPerBox() 
  public Integer getBeverageBoxPrice() 
  public List<Integer> getBeveragesId() 
  public Map<Integer, String> getBeverages() 
  public Integer getBeverageCapsules()
  public Integer createEmployee()
  public void updateEmployee()
  public String getEmployeeName() 
  public String getEmployeeSurname() 
  public Integer getEmployeeBalance() 
  public List<Integer> getEmployeesId() 
  public Map<Integer, String> getEmployees() 
  public Integer getBalance() 
  public void reset()
  }
class Exception {
}
note top of Exception: standard library definition
class BeverageException{
  private static final long serialVersionUID
} 
class DateException{
  private static final long serialVersionUID
} 
class EmployeeException{
  private static final long serialVersionUID
} 
class  NotEnoughBalance{
  private static final long serialVersionUID
} 
class  NotEnoughCapsules{
  private static final long serialVersionUID
} 

DataInterface -down-o LaTazza
GUI --o LaTazza
Exception --o DataInterface
BeverageException --|> Exception
DateException --|> Exception
EmployeeException --|> Exception
NotEnoughBalance --|> Exception
NotEnoughCapsules --|> Exception

```
*Note 1: General Class Diagram*

```plantuml
class DataInterface{
}
note right: defined in the previous UML Class Diagram
class DataImplementation{
  private float balance
  private Map<Integer,Employee> employeeMap
  private Map<Integer,Beverage> beverageMap
  private static int idGlobalEmployee
  private static int idGlobalBeverage
  
  private Statement stmt
  privare ResultSet rset
  private Sitrng jdbcUrl
  private String userid
  private String password
  private Connection conn
  private String path
  private float overflow
  private float max
  private float min

  private Connection getConnection()
  private void insertB(int , String, int , Float, int)
  private void insertE(int , String , String , Float) 
  private void insertT(String , Timestamp , String , String , int ,float Amount) 
  private void updateB(int , String , int , Float , int ) 
  private void updateE(int , String , String, Float ) 
  public DataImpl()

  public Integer sellCapsules(Integer , Integer , Integer , Boolean )
  public void sellCapsulesToVisitor(Integer , Integer )
  public Integer rechargeAccount(Integer , Integer ) 
  public void buyBoxes(Integer , Integer ) 
  public List<String> getEmployeeReport(Integer , Date , Date )
  public List<String> getReport(Date , Date ) 
  public Integer createBeverage(String , Integer , Integer )   
  public void updateBeverage(Integer , String , Integer , Integer )
  public String getBeverageName(Integer )  
  public Integer getBeverageCapsulesPerBox(Integer )  
  public Integer getBeverageBoxPrice( Integer )
  public List<Integer> getBeveragesId( ) 
  public Map<Integer, String> getBeverages() 
  public Integer getBeverageCapsules(Integer)
  public Integer createEmployee(String,String)
  public void updateEmployee(Integer, String, String )
  public String getEmployeeName(Integer) 
  public String getEmployeeSurname(Integer) 
  public Integer getEmployeeBalance(Integer) 
  public List<Integer> getEmployeesId() 
  public Map<Integer, String> getEmployees() 
  public Integer getBalance() 
  public void reset()
}

class Database{
}

class Employee{
  private int ID
  private String Name
  private String Surname
  private float Balance
  public Employee (int, String, String ,float)
  public int getID ()
  public String getName()
  public String getSurname()
  public float getBalance()
  public void setBalance( float  )
  public void setName (String )
  public void setSurname (String )
  

}
class Beverage{
  private int ID
  private String Name
  private int Capsules
  private float Price 
  private int CapsulesPerBox
  private float BoxPrice
  private int oldRemainingCap
  private int newRemainignCap
  private float newPirce
  private boolean newValuesSet
  public Beverage(int, string , int,float, int ,float)
  public int getID ()
  public String getName()
  public int getRemainingCapsules()
  public void setRemainingCapsules(int )
  public float getPrice()
  public void setName(String )
  public void setPrice(String )
  public int getCapsulesPerBox()
  public void setCapsulesPerBox(int )
  public float getBoxPrice()
  public void setBoxPrice(float )
  public void setNewRemainingCap (int)
  public void setNewPrice ( float)
  public void swap ()
  public void setFlagNewPrice()
  public float getNewPrice()
  public boolean isNewValuesset ()
  public int getOldRemainingCap()
  public void setOldRemainingCap(int)
}


DataImplementation --|> DataInterface :Extends
Employee --o DataImplementation
Beverage --o DataImplementation
Database --o DataImplementation
```
*Note 2: DataImplementation Class Diagram*

*Note 3: The Façade design pattern is used*


# Verification traceability matrix

|   | Database  | Employee  | Beverage  | DataImplementation  | LaTazza  | GUI  | Exception  | DataInterface  |
|---|---|---|---|---|---|---|---|---|
| FR1  |   | X  |  X | X  |   |   |   |   |
| FR2  |   |   |   | X  |   |   |   |   |
| FR3  |   | X  |   | X  |   |   |   |   |
| FR4  |   |   | X  |  X |   |   |   |   |
| FR5  | X  | X  |   | X  |   |   |   |   |
| FR6  |  X |   |   | X  |   |   |   |   |
| FR7  |   |   | X  | X  |   |   |   |   |
| FR8  |   |  X |   | X  |   |   |   |   |



# Verification sequence diagrams 

```plantuml
": LaTazza" -> ": dataImplementation": "1: getBeverageName()"
": LaTazza" -> ": dataImplementation": "2: getEmployeeName()"
": LaTazza" -> ": dataImplementation": "3: sellCapsule()"
note over ": dataImplementation": Selling OK
": dataImplementation" -> ": beverage": "3.1: updateBeverage()"
": dataImplementation" -> ": beverage": "3.2: getPrice()"
": dataImplementation" -> ": employee": "3.3: updateEmployee()"
```
*Note 1: Sequence Diagram Scenario 1*

```plantuml
": LaTazza" -> ": dataImplementation": "1: getBeverageName()"
": LaTazza" -> ": dataImplementation": "2: getEmployeeName()"
": LaTazza" -> ": dataImplementation": "3: sellCapsule()"
note over ": dataImplementation": Selling FAILED
participant ": beverage"
participant ": employee"
```

*Note 2: Sequence Diagram Scenario 2*