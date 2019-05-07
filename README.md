# Talent-Tracking-Database
This program scans PDF resumes using the Java PDFbox library to identify job skills required by a client. Users can create databases (e.g., a database for each open position), deposit resumes into databases, and view either all candidates in the database or just those candidates which meet user-defined search criteria.

<b>Using the Program</b>

Primary GUI

Running the program prompts the display of a graphical user interface, as shown in the image below. Users can click on three buttons: Create Database, Deposit Resumes, or View Candidates. Each button generates a different pop-up menu.

![Image of the Primary GUI](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/1%20-%20Primary%20GUI.PNG)

Create Database Menu

Clicking the “Create Database” button causes a pop-up menu to appear, as shown in the image below. Users can enter the name of a database to create, as well as which skills (e.g., Java, for a Java development position) should be tracked using that database. Users should separate skills to be tracked in the database using a space (e.g., Java Python SQL). Users click on the “Create Database” button in the pop-up menu to create a database.

![Image of the Create Database Menu](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/2%20-%20Create%20Database.PNG)

A pop-up message informs the reader if database creation is successful.

![Image of Database Creation Success Message](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/3%20-%20Database%20Message.PNG)

Deposit Resumes Menu

Clicking the “Deposit Resumes” button on the primary GUI causes a pop-up menu to appear. Users can enter the name of the database into which resumes should be deposited in the “Database” text box. File paths to resumes can be inserted into the large text area under the heading “Resumes To Be Deposited.” Users should separate resume file paths with a new line.

![Image of Deposit Resumes Menu](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/4%20-%20Deposit%20Resumes.PNG)

Users can click the “Browse” button to use a pop-up window to choose files to add to the “Resumes To Be Deposited” text area.

![Image of Browse Button](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/5%20-%20Browse%20Button.PNG)

The program attempts to deposit resumes into the database when the user clicks the “Deposit Resumes” button. If no SQL error occurs, the program generates a pop-up window informing the user that the resumes were successfully deposited.

![Image of Deposit Resumes Success Message](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/6%20-%20Deposit%20Message.PNG)

When the program deposits resumes, it also searches those resumes for strings that correspond to the skills stored in the database. If the program finds a skill, it sets the value of that skill’s column in the database to “Yes” for that resume. If the program does not find the skill, it sets the value of the corresponding column to “No” for that resume.

View Candidates Menu

Clicking the “View Candidates” button on the primary GUI causes a pop-up menu to appear. Users can enter the name of the database from which to return candidates in the “Database” text box.

![Image of View Candidates Menu](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/7%20-%20View%20Candidates.PNG)

If a user wants to view only candidates with certain skills, the user can specify those skills in the “Select Skills” text box. In the example below, searching for only resumes which contain Java, Python, and SQL using the “Show Resumes With Skills” button returns only one resume. The name of the resume is displayed in a table along with columns corresponding to whether the program found each of the searched skills in the resume.

![Image of Table Returned by Specific Skill Search](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/8%20-%20Resumes%20With%20Skills.PNG)

If a user wants to see all candidates in a database, the user can click the “Show All Candidates” button. This will return a table containing all resumes in a database, whether or not the user has entered preferred skills. Rows corresponding to resumes for which the program found all skills tracked by the database are highlighted in yellow and contain bolded text.

![Image of Table Returned by All Candidates Search](https://github.com/wolferobert3/Talent-Tracking-Database/blob/master/Usage%20Images/9%20-%20All%20Resumes.PNG)

Automatically Populated Database Name

The program uses a config.properties file to track the last database name entered by a user. When a user creates a database, deposits resumes, or views candidates, the program writes the most recently entered name of a database to the config.properties file. When a user opens the Deposit Resumes or View Candidates menus, the program automatically populates the database name on these screens with the database name stored in the config.properties file.

Libraries and Tools

The program was built using Java 8, which can be downloaded at: https://www.java.com/en/download/

The program employs the functionality of Java’s Swing library to create its GUI.

The program also uses functionality from Apache PDFBox, which can be downloaded at: https://pdfbox.apache.org/

The program was built using the Eclipse IDE, which can be downloaded at: https://www.eclipse.org/

The program uses PostgreSQL for its database, which can be downloaded at: https://www.postgresql.org/

Authorship

This program was created by Robert Wolfe.
