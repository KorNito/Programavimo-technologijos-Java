package java_2_laboratorinis;

import ds.*;

public class Java_2_laboratorinis {

    public static void main(String[] args) {
        ToDoList todo = new ToDoList();

        todo.registerPerson("adminas", "admin", "Adminas", "Password");
        todo.registerPerson("root", "admin", "Adminas", "Password");
        User u = todo.registerPerson("admin", "admin", "Adminas", "Password");
        System.out.println(u);
        try{
        todo.login("admin", "admin");
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        boolean pavyko = todo.disableUser(2);
        System.out.println("Pavyko istrinti useri? " + pavyko);

        todo.editPersonInfo(1, "jonas", "jonaitis");

        for (User t : todo.getAllActiveUsers()) {
            System.out.println(t);
        }

        todo.addProject("Java ld");
        todo.getAllUserProjects();
        todo.addProjectMember(1, 3);
        todo.deleteProject(1);
        Task t1 = todo.addTaskToProject(1, "Uzduotis");
        todo.addTaskToProject(t1.getId(), "Uzduotis");

        /*
        todo.registerCompany(); to do
        todo.getProjectMembers(); to do
        todo.editProjectInfo(); to do
        todo.getAllProjects(); to do
        todo.getProjectTasks(); to do
        todo.completeTask(); to do
         */
    }

}
