package com.example.sprdeploy.controller;

import com.example.sprdeploy.entities.Message;
import com.example.sprdeploy.entities.User;
import com.example.sprdeploy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")//сделать приветствие с username
    public String greeting(@AuthenticationPrincipal User user,Map<String, Object> model){
        if (user!=null) {
            model.put("greet", user.getUsername());
        }else model.put("greet","User");
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user ,@RequestParam(required = false,defaultValue = "") String filter, Model model){

        Iterable<Message> messages;
        if (filter!=null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        }else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages",messages);
        model.addAttribute("filter",filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Map<String,Object> model,
                      @RequestParam("file") MultipartFile file) throws IOException {

       Message message = new Message(text,tag,user);

       if (file!= null && !file.getOriginalFilename().isEmpty()){
           File uploadDir  = new File(uploadPath);

           if (!uploadDir.exists()){
               uploadDir.mkdirs();
           }
           String uuidFile = UUID.randomUUID().toString();
           String resultFileNme = uuidFile+"."+file.getOriginalFilename();
           file.transferTo(new File(uploadPath + "/" +resultFileNme));
           message.setFilename(resultFileNme);
       }

       if (!message.getTag().isEmpty()&&!message.getText().isEmpty()) {
           messageRepository.save(message);
       }else {
           model.put("alarm","Enter tag & text");
       }

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages",messages);

        return "main";
    }

}
