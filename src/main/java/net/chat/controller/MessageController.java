package net.chat.controller;

import net.chat.entity.Message;
import net.chat.service.MessageService;
import net.chat.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sergey on 3/31/14.
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveMessage(@RequestParam(value = "fromUserId") Long fromUserId,
                              @RequestParam(value = "toUserId") Long toUserId,
                              @RequestParam(value = "messageTxt") String messageTxt) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Message message = new Message(fromUserId, toUserId, messageTxt, date, false);

        messageService.save(message);

        return "{'result':'true'}";
    }

    @RequestMapping(value = "/get/message/by/user/id", method = RequestMethod.POST)
    @ResponseBody
    public String getNotReadMessages(@RequestParam(value = "fromUserId") Long fromUserId,
                                     @RequestParam(value = "toUserId") Long toUserId) {

        List<Message> messages = messageService.getNotReadMessages(fromUserId, toUserId);

        return "{'result':" + JsonUtil.toArray(messages).toString() + "}";
    }

    @RequestMapping(value = "/mark/messages/as/read", method = RequestMethod.POST)
    @ResponseBody
    public String markMessagesAsRead(@RequestParam(value = "messageIds") String messageIds) {
        List<Long> ids = new ArrayList<Long>();
        String[] strArray = messageIds.split(";");
        for (String item : strArray)
            ids.add(new Long(item));

        messageService.markMessagesAsRead(ids);

        return "{'result': 'true'}";
    }

    @RequestMapping(value = "/get/count/messages", method = RequestMethod.POST)
    @ResponseBody
    public String getCountMessages(@RequestParam(value = "currentUserId") Long currentUserId) {

        messageService.getCountMessages(currentUserId);

        return "{'result': 'true'}";
    }
}
