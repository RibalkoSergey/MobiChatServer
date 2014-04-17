package net.chat.controller;

import net.chat.entity.User;
import net.chat.service.UserService;
import org.apache.commons.codec.DecoderException;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


/**
 * Created by sergey on 2/11/14.
 */
@Controller
public class AdministrationController {

    @Autowired
    private UserService userService;

   /* @RequestMapping(value = "/administration/register", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(HttpServletRequest request,
                               @RequestParam(value = Constants.FIRST_NAME) String firstName,
                               @RequestParam(value = Constants.LAST_NAME) String lastName,
                               @RequestParam(value = Constants.LOGIN) String login,
                               @RequestParam(value = Constants.PASSWORD) String password,
                               @RequestParam(value = Constants.EMAIL) String email,
                               @RequestParam(value = "image") String file) {

        //----assert------
        byte[] byteFile = null;

        try {
            byteFile = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User("sergey", "sergey", "sergey", "sergey", "sergey@sergey.ru", true, byteFile);

        userService.registerUser(user);
        request.getSession().setAttribute("user", user);

        return user.toJson();
    }*/

    @RequestMapping(value = "/administration/register1", method = RequestMethod.POST)
    @ResponseBody
    public String register1(HttpServletRequest request,
                                @RequestParam(value = "image") String image,
                                @RequestParam(value = "first_name") String first_name,
                                @RequestParam(value = "last_name") String last_name,
                                @RequestParam(value = "login") String login,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "email") String email) {

        //----assert------
        //byte[] byteFile = null;

        //try {
        //    byteFile = image.getBytes();

        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        User user = new User(first_name, last_name, login, password, email, true, image);
        user.setPhoto(image);
        userService.registerUser(user);

        request.getSession().setAttribute("user", user);

        return user.toJson().toString();
    }

    @RequestMapping(value = "/administration/register3", method = RequestMethod.POST, headers={"content-type=multipart/form-data"})
    @ResponseBody
    public void uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) {

        //----assert------
        byte[] byteFile = null;

        try {
            byteFile = file.getBytes();
            //byteFile = Hex.decodeHex(file.toCharArray());
            //byteFile = DatatypeConverter.parseHexBinary(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*User user = new User("sergey", "sergey", "sergey", "sergey", "sergey@sergey.ru", true, byteFile);
        user.setPhoto(byteFile);
        userService.registerUser(user);

        request.getSession().setAttribute("user", user);*/
    }

    @RequestMapping(value = "/administration/register2", method = RequestMethod.POST)
    @ResponseBody
    public void register2(HttpServletResponse response) throws IOException, DecoderException {
        //---------- работающая штука тип в базе - BLOB

        /*User user = userService.getUserBylogin("sergey1");
        //----assert------
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(user.getLogin(), "UTF-8").replaceAll("[+]", " ") + "\"");
        response.setContentType("application/octet-stream");
        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");
        response.setContentLength(user.getPhoto().length);

        FileCopyUtils.copy(user.getPhoto(), response.getOutputStream());*/
    }

    @RequestMapping(value = "/administration/register4", method = RequestMethod.POST)
    @ResponseBody
    public String login() {

        User user = userService.getUserBylogin("sergey1");
        //String foto = Base64.encodeBase64String(user.getPhoto());
        //String foto = DatatypeConverter.printBase64Binary(user.getPhoto());
        return "{'foto':'" + user.getPhoto() + "'}";
    }

    @RequestMapping("/add3/{password}")
    @ResponseBody
    public String add(HttpServletRequest request,

                      @PathVariable("password") Integer password) {
        User user1 = (User)request.getSession().getAttribute("user");
        User user = new User();
        //user.setName("www");
        user.setPassword(String.valueOf(password));
        user.setLogin("qqq");
        userService.addUser(user);
        request.getSession().setAttribute("user", user);
        String s = request.getSession().getId();

        return "{'id':" + s + "}";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request,
                        @RequestParam("login") String login,
                        @RequestParam("password") String password) {

        JSONObject result = new JSONObject();

        User currentUser = userService.loginUser(login, password);
        if (currentUser != null) {
            request.getSession().setAttribute("currentUser", currentUser);
            result = currentUser.toJson();
        } else {
            result.put("error", "Wrong password.");
        }

        return result.toString();
    }
}
