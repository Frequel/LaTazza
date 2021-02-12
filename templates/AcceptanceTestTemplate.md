# Acceptance Testing Documentation template

Authors: Angione Francesco, Butera Alberto, Di Fabio Matteo, Forese Leonardo

Date: 21/05/2019
 
Version: 1.0.1

# Contents

- [Scenarios](#scenarios)

- [Coverage of scenarios](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Scenarios


| Scenario ID: SC1 | Corresponds to UC1                             |
| ---------------- | ---------------------------------------------- |
| Description      | Colleague uses one capsule of type 1 ->FR1          |
| Precondition     | account of C has enough money to buy capsule T |
| Postcondition    | account of C updated, count of T updated       |
| Step#            | Step description                               |
| 1                | Administrator selects capsule type T           |
| 2 |				Administrator inserts the number of desired capsules to buy |
| 3               | Administrator selects colleague C              |
| 4 | Administrator selects buy with credits | 
| 5 | Administrator clicks on the button sell  |
| 6                | Subtract the quantity of the bought capsule T from the avialable ones           |
| 7                | Deduce price of T from account of C            |

| Scenario ID: SC2 | Corresponds to UC2                                     |
| ---------------- | ------------------------------------------------------ |
| Description      | Colleague uses one capsule of type T, account negative -> FR1 |
| Precondition     | account of C has not enough money to buy capsule T     |
| Postcondition    | account of C updated, count of T updated               |
| Step#            | Step description                                       |
| 1                | Administrator selects capsule type T                   |
| 2 |				Administrator inserts the number of desired capsules to buy |
| 3                | Administrator selects colleague C                      | 
| 4 | Administrator selects buy with credits | 
| 5 | Administrator clicks on the button sell  |
| 6               | Subtract the quantity of the bought capsule T from the avialable ones                   |
| 7                | Deduce price of T from account of C                    |
| 8                | Account of C is negative, issue warning, the account balance goes in negative       |

| Scenario ID: SC3 | Corresponds to UC3 |
| ---------------- | ------------------ |
| Description      | Record recharge of account of colleague   -> FR3          |
| Precondition     | Account A exists            |
| Postcondition    |	A.amount_post > A.amount_pre      |
| Step#            |  Step description                |
| 1                | Administrator selects colleague C                |
| 2                 |  Administrator inserts  the amount of money to recharge on the employee's account  |
| 3 | Administrator clicks on payment button |


| Scenario ID: SC4 | Corresponds to UC4 |
| ---------------- | ------------------ |
| Description      | Record purchase of capsules -> FR4              |
| Precondition     |  Capsule (Beverage) type CT exists             |
| Postcondition    |	 CT.quantity_post > CT.quantity _pre        |
| | LaTazzaAccount.balance_post < LaTazzaAccount.balance_pre|
| Step#            |  Step description                |
| 1 |  Select the Beverage ct  |
| 2 | Insert the number of boxes to buy |
| 3 | Click on the buy button |


| Scenario ID: SC5 | Corresponds to UC5 |
| ---------------- | ------------------ |
| Description      | Produce report on consumption of colleague -> FR5              |
| Precondition     |  Colleague C exists            |
| Postcondition    |        |
| Step#            |  Step description                |
| 1  | Click on the menù tab  |
| 2  | Select log command |
| 3 | Select an Employee c on the right  |
| 4 | Insert the data range |
| 5 | Click on the get emplyee report button   |


| Scenario ID: SC6 | Corresponds to UC6 |
| ---------------- | ------------------ |
| Description      | Produce report on all consumptions -> FR6              |
| Precondition     |              |
| Postcondition    |         |
| Step#            |  Step description                |
| 1  | Click on the menù tab  |
| 2  | Select log command |
| 3 | Insert the data range  in the left part |
| 5 | Click on the get report button   |


| Scenario ID: SC7 | Corresponds to UC7 |
| ---------------- | ------------------ |
| Description      | Record that a visitor has used n capsules of a certain type -> FR2              |
| Precondition     |  Capsules ( beverage ) ct exist            |
| Postcondition    |   Post_cash_account > pre_cash_account post_beverage_n_capsules < pre_beverage_n_capsules      |
| Step#            |  Step description                |
| 1                | Administrator selects capsule type T           |
| 2 | Administrators inserts the number of desired capsules to buy | 
| 3 | Administrator clicks on the button sell  |
| 4               | Subtract the quantity of the bought capsule T from the avialable ones         |
| 5                | Update the cash account           |



| Scenario ID: SC8 |  |
| ---------------- | ------------------ |
| Description      | Add a beverage -> FR7              |
| Precondition     |             |
| Postcondition    |  the beverage has been added correctly      |
| Step#            |  Step description                |
| 1                | Administrator selects edit menù           |
| 2 | Administrator clicks on the beverage row  | 
| 3 | Administrator inserts the data for the beverage  |
| 4 | Administrator clicks on insert | 




| Scenario ID: SC9 |  |
| ---------------- | ------------------ |
| Description      | update a beverage -> FR7              |
| Precondition     |             |
| Postcondition    |  the beverage has been updated correctly      |
| Step#            |  Step description                |
| 1                | Administrator selects edit menù           |
| 2 | Administrator clicks on the beverage row  | 
| 3 | Administrator inserts the data for the beverage update |
| 4 | Administrator clicks on update | 


| Scenario ID: SC10 |  |
| ---------------- | ------------------ |
| Description      |  add an employee -> FR8              |
| Precondition     |             |
| Postcondition    |  the employee has been added correctly      |
| Step#            |  Step description                |
| 1                | Administrator selects edit menù           |
| 2 | Administrator clicks on the employee row  | 
| 3 | Administrator inserts the data for the employee |
| 4 | Administrator clicks on insert | 


| Scenario ID: SC11 |  |
| ---------------- | ------------------ |
| Description      | update an employee -> FR8              |
| Precondition     |             |
| Postcondition    |  the employee has been updated correctly      |
| Step#            |  Step description                |
| 1                | Administrator selects edit menù           |
| 2 | Administrator clicks on the employee row  | 
| 3 | Administrator inserts the data for the employee update |
| 4 | Administrator clicks on update | 


*Note 1: in the scenario also the actions on the GUI are added.*
*Note 2: the variants of scenario in which something goes wrong are not considered since the functional requirements coverage are testing.*

# Coverage of Scenarios

```
<Report in the following table the coverage of the scenarios listed above. Report at least an API test (defined on the functions of DataImpl only) and a GUI test (created with EyeAutomate) for each of the scenarios. For each scenario, report the functional requirements it covers.
In the API Tests column, report the name of the method of the API Test JUnit class you created. In the GUI Test column, report the name of the .txt file with the test case you created.>
```

### 

| Scenario ID | Functional Requirements covered | API Test(s) | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | ----------- |
| 1           | FR1  - FR7 - FR8  -FR3         |   testSC1          |    Scenario1 - Scenario8 - Scenario9 - Scenario10 - Scenario11      |
| 2           | FR1  - FR7 - FR8  -FR3  particular case of the previous one      | testSC1a            |    Scenario2 - Scenario8 - Scenario9 - Scenario10 - Scenario11        |
|	3         |  FR3 - FR8                           |     testSC1 and testSC1a       |    Scenario3  - Scenario8 - Scenario9 - Scenario10 - Scenario11       |
| 4         |   FR4 - FR7                              |  testSC1 and testSC1a           |  Scenario4 - Scenario8 - Scenario9 - Scenario10 - Scenario11          |
| 5         |     FR5                            |   testSC5          |        Scenario5  - Scenario8 - Scenario9 - Scenario10 - Scenario11   |
| 6         |      FR6                           |   testSC6       |      Scenario6 - Scenario8 - Scenario9 - Scenario10 - Scenario11   |
| 7 |   FR2 -FR7 | testSC7 | Scenario7 - Scenario8 - Scenario9 - Scenario10 - Scenario11|


*Note: the tests on the getReport and getEmployeeReport will not test the seconds of the timestamp in order to be system-load independent.*


# Coverage of Non Functional Requirements


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|      NFR1                      |     not testable      |
| NFR2 | testNFR2inFRi , with i from 1 to 5 covering the previous test cases of the scenario. All in the systemTests class | 
| NFR3 | not testable | 
| NFR4 | not testable | 
