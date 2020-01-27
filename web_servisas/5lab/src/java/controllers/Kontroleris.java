package controllers;

import com.google.gson.Gson;
import ds.Project;
import ds.ToDoList;
import ds.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Kontroleris {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody String user) throws Exception {
        ToDoList todo = new ToDoList();
        todo.connectToDB();
        Gson parser = new Gson();
        User paduotas = (User) parser.fromJson(user, User.class);
        User current = todo.login(paduotas.getLogin(), paduotas.getPass());
        var userJson = parser.toJson(current);

        if (current != null) {
            todo.disconect();
            return userJson;
        } else {
            todo.disconect();
            return "Neteisingi prisijungimo duomenys. Contact support";
        }
    }

    @RequestMapping(value = "user_{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String userDetails(@PathVariable("userId") int userId) {
        ToDoList todo = new ToDoList();
        todo.connectToDB();

        if (todo.isLogged(userId)) {
            User current = todo.getUserById(userId);
            Gson parser = new Gson();
            String userInfoJson = null;
            userInfoJson = parser.toJson((User) current);

            todo.disconect();
            return userInfoJson;
        } else {
            todo.disconect();
            return "Negalite matyti duomenu. Contact support";
        }
    }

    @RequestMapping(value = "deleteProject_{userId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeProject(@RequestBody Project project, @PathVariable("userId") int userId) {
        ToDoList todo = new ToDoList();
        todo.connectToDB();

        if (todo.isLogged(userId)) {
            todo.deleteProject(project.getId());
            todo.disconect();
            return "Projektas istrintas";
        } else {
            todo.disconect();
            return "Nepavyko istrinti projekto. Contact support";
        }

    }
}
