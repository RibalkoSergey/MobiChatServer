package net.chat.controller;

import net.chat.entity.User;
import net.chat.entity.UserViewBean;
import net.chat.service.UserService;
import net.chat.utils.JsonUtil;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by sergey on 2/3/14.
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add1", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") User user,
                             BindingResult result) {

        userService.addUser(user);

        return "redirect:/index";
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping("/add/{password}")
    public String deleteContact(@PathVariable("password") Integer password) {
        User user = new User();
        //user.setName("www");
        user.setPassword(String.valueOf(password));
        user.setLogin("qqq");
        userService.addUser(user);

        return "index";
    }

    @RequestMapping("/add2/{password}")
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

    @RequestMapping(value = "/get/friends/list", method = RequestMethod.POST)
    @ResponseBody
    public String getFriendList(@RequestParam(value = "currentUserId") Long currentUserId) {

        List<UserViewBean> friendsList = userService.getFriendsListByUser(currentUserId);

        return "{'result':" + JsonUtil.toArray(friendsList).toString() + "}";
    }

    @RequestMapping(value = "/get/foto", method = RequestMethod.POST)
    @ResponseBody
    public String getAvatar(@RequestParam("id") Long id) {

        User user = userService.getUserById(id);

        return "{'result':'" + user.getPhoto() + "'}";
    }

    @RequestMapping(value = "/get/friends/list/by/name", method = RequestMethod.POST)
    @ResponseBody
    public String getFriendList(@RequestParam(value = "name") String name) {

        List<UserViewBean> friendsList = userService.getFriendsListByName(name);

        return "{'result':" + JsonUtil.toArray(friendsList).toString() + "}";
    }

    @RequestMapping(value = "/send/invite", method = RequestMethod.POST)
    @ResponseBody
    public String getFriendList(@RequestParam(value = "fromUserId") Long fromUserId,
                                @RequestParam(value = "toUserId") Long toUserId) {

        userService.sendInvite(fromUserId, toUserId);
        return "{'result':'true'}";
    }

    @RequestMapping(value = "/get/invites", method = RequestMethod.POST)
    @ResponseBody
    public String getInvite(@RequestParam(value = "currentUserId") Long currentUserId) {

        List<UserViewBean> friendsList = userService.getInvite(currentUserId);
        return "{'result':" + JsonUtil.toArray(friendsList).toString() + "}";
    }

    @RequestMapping(value = "/delete/invite", method = RequestMethod.POST)
    @ResponseBody
    public String deleteInvite(@RequestParam(value = "currentUserId") Long currentUserId,
                            @RequestParam(value = "friendId") Long friendId) {

        userService.deleteInvite(currentUserId, friendId);
        return "{'result':'true'}";
    }

    @RequestMapping(value = "/add/friend", method = RequestMethod.POST)
    @ResponseBody
    public String addFriend(@RequestParam(value = "currentUserId") Long currentUserId,
                            @RequestParam(value = "friendId") Long friendId) {

        userService.addFriend(currentUserId, friendId);
        userService.deleteInvite(currentUserId, friendId);
        return "{'result':'true'}";
    }
}
