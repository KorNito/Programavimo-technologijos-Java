package ds;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;

public class ToDoList implements Serializable {

    private ArrayList<User> users = new ArrayList();
    private ArrayList<Project> projects = new ArrayList();
    private User loggedIn = null;
    private Connection con = null;

    public Person registerPerson(String login, String pass, String name, String surname) {
        if(isLoginOcupied(login)){
            return null;
        }
            Person newPerson = new Person(login, pass, name, surname);
            users.add(newPerson);
            try{
                PreparedStatement ps = con.prepareStatement("INSERT INTO user (id, login, password, email) "
                        + "VALUES (NULL, ?, ?, MD5(?), ?)", Statement.RETURN_GENERATED_KEYS);
                
                ps.setBoolean(1, true);
                ps.setString(2, login);
                ps.setString(3, pass);
                ps.setInt(4, 0);
                
                ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                
                ids.next();
                int ID = ids.getInt(1);
                
                ps = con.prepareStatement("INSERT INTO person (id, name, lastname) "
                        + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, ID);
                ps.setString(2, name);
                ps.setString(3, surname);
                
                ps.executeUpdate();
                ids.close();
                ps.close();
            }
            catch(Exception ex){
                
            }
            return newPerson;
    }
    
    public Company registerCompany(String login, String pass, String name) {
        if(isLoginOcupied(login)){
            return null;
        }
            Company newCompany = new Company(login, pass, name);
            users.add(newCompany);
            try{
                PreparedStatement ps = con.prepareStatement("INSERT INTO user (id, login, password) "
                        + "VALUES (NULL, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                
                ps.setBoolean(1, true);
                ps.setString(2, login);
                ps.setString(3, pass);
                ps.setInt(4, 0);
                
                ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                
                ids.next();
                int ID = ids.getInt(1);
                
                ps = con.prepareStatement("INSERT INTO company (id, name) "
                        + "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, ID);
                ps.setString(2, name);
               
                
                ps.executeUpdate();
                ids.close();
                ps.close();
            }
            catch(Exception ex){
                
            }
            return newCompany;
    }
    
    public boolean isLoginOcupied(String login) {
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE login=?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, login);
            ResultSet duom = ps.executeQuery();
            while(duom.next()){
                duom.close();
                ps.close();
                return true;
            }
            duom.close();
            ps.close();
            return false;
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
    }

    public User login(String login, String pass) throws Exception {
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPass().equals(pass) && u.isActive()) {
                loggedIn = u;
                return u;
            }
        }
        throw new ObjectNotExists("incorrect login data");
    }

    public void logout(int id) {
        loggedIn = null;
    }

    public Project addProject(String title) {
        if (loggedIn != null && title.length() > 3) {
            Project newProject = new Project(title, loggedIn);
            projects.add(newProject);
            loggedIn.addProjects(newProject);
            return newProject;
        }
        return null;
    }

    public void addProjectMember(int projectID, int userId) {
        if (loggedIn != null) {
            Project current = getProjectById(projectID);
            User toAdd = getUserById(userId);
            if (current != null && toAdd != null) {
                current.addMember(toAdd);
                toAdd.addProjects(current);
            }
        }
    }

    public void deleteProject(int id) {
        Project current = getProjectById(id);
        if (current != null) {
            projects.remove(current);
            for (User u : current.getMembers()) {
                u.getProjects().remove(current);
            }
        }
    }

    public Task addTaskToProject(int projectId, String title) {
        if (loggedIn != null) {
            Project my = getUserProjectById(projectId);
            Task newTask = new Task(title, my, loggedIn);
            my.addTask(newTask);
            return newTask;
        }
        return null;
    }

    public Task addTaskToTask(int taskId, String title) {
        if (loggedIn != null) {
            Task my = getUserTaskById(taskId);
            Task newTask = new Task(title, my.getProject(), loggedIn);
            my.addTask(newTask);
            return newTask;
        }
        return null;
    }

    public Task getUserTaskById(int id) {
        for (Project p : loggedIn.getProjects()) {
            ArrayList<Task> allTasks = p.getAllTasks();
            for (Task t : allTasks) {
                if (t.getId() == id) {
                    return t;
                }
            }
        }

        return null;
    }

    public Project getUserProjectById(int id) {
        if (loggedIn != null) {
            for (Project p : loggedIn.getProjects()) {
                if (p.getId() == id) {
                    return p;
                }
            }
        }
        return null;
    }

    public ArrayList<Project> getAllUserProjects() {
        if (loggedIn != null) {
            return loggedIn.getProjects();
        }
        return new ArrayList();
    }

    public void editPersonInfo(int id, String name, String surname) {
        User current = getUserById(id);
        if (current != null && current.getClass().equals(Person.class)) {
            Person p = (Person) current;
            p.setName(name);
            p.setSurname(surname);
        }
    }

    public ArrayList<User> getAllUsers() {
        if (loggedIn != null && loggedIn.isActive()) {
            return users;
        }
        return new ArrayList();
    }

    public ArrayList<User> getAllActiveUsers() {
        if (loggedIn != null && loggedIn.isActive()) {
            ArrayList<User> filtered = new ArrayList();
            for (User u : users) {
                if (u.isActive()) {
                    filtered.add(u);
                }
            }
            return filtered;
        }
        return new ArrayList();
    }

    public boolean disableUser(int id) {
        if (loggedIn != null && loggedIn.isActive()) {
            User forDeletion = getUserById(id);
            if (forDeletion != null && forDeletion.isActive()) {
                forDeletion.setActive(false);
                return true;
            }
        }
        return false;

    }

    public User getUserById(int id) {
        if (loggedIn != null && loggedIn.isActive()) {
            for (User u : users) {
                if (u.getId() == id) {
                    return u;
                }
            }
        }
        return null;
    }
    
        public boolean isLogged(int userId){
        try{
            var prep = con.prepareStatement("SELECT * FROM user WHERE id=?");
            prep.setInt(1, userId);
            ResultSet duom = prep.executeQuery();
            
            while(duom.next()){
                return true;
            }
            
            prep.close();
            duom.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public Project getProjectById(int id) {
        return projects.get(0);  //To Do
    }

    public User getUserByLogin(String login) {
        if (loggedIn != null && loggedIn.isActive()) {
            for (User u : users) {
                if (u.getLogin().equals(login)) {
                    return u;
                }
            }
        }
        return null;
    }

    public int[] getUserCount() {
        int[] num = new int[2];
        for (User u : users) {
            if (u.getClass().equals(Person.class)) {
                num[0]++;
            } else {
                num[1]++;
            }
        }
        return num;
    }

    public int[][] getProjectNumbers() {
        int[][] mas = new int[projects.size()][2];
        int id = 0;
        for (Project p : projects) {
            mas[id][0] = p.getId();
            mas[id][1] = p.getAllTasks().size();
            id++;
        }
        return mas;
    }
    
    public void connectToDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/laboratorinis4", "root", ""); 
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void disconect(){
        try{
            if(con!=null)
                con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void deleteTask(Task currentTask) {
        try{
            var prep = con.prepareStatement("DELETE FROM task WHERE task.`project_id` = ?");
            prep.setInt(1, currentTask.getId());
            prep.executeQuery();
            
            prep.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void completeTask(Project project, Task task, User user) {
        try {
            var prep = con.prepareStatement("UPDATE task SET completedOn = ? WHERE task.`id` = ?", Statement.RETURN_GENERATED_KEYS);
            java.util.Date today = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
            prep.setTimestamp(1, timestamp);
            prep.setInt(2, task.getId());
            prep.executeQuery();
            
            prep.close();
        }catch(SQLException ex) {
            throw null;
        }
    }

}
