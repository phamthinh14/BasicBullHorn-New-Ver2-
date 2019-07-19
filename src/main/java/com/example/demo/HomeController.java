package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listMessage(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    @GetMapping("/addnew")
    public String newMessageForm(Model model) {
        model.addAttribute("message", new Message());
        return "newmessageform";
    }

    @PostMapping("/process")
    public String processNewFormPic(@ModelAttribute @Valid Message message, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "newmessageform";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            message.setHeadshot(uploadResult.get("url").toString());
            messageRepository.save(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "newmessageform";
        }
        return "redirect:/";
    }

    @PostMapping("/addnew")
    public String processNewFormText(@ModelAttribute @Valid Message message, BindingResult result) {
        if (result.hasErrors()) {
            return "newmessageform";
        }
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/addnew")
    public String index1() {
        return "list";
    }

    @RequestMapping("/add")
    public String index() {
        return "list";
    }


    @GetMapping("/add")
    public String messageForm(Model model) {
        model.addAttribute("message", new Message());
        return "messageform";
    }


    @PostMapping("/add")
//    cHANGE THE MESSAGES
    public String processForm(@ModelAttribute @Valid Message message, BindingResult result) {

        String myUrl;
        if (result.hasErrors()) {
            return "messageform";
        }

        myUrl = messageRepository.findById(message.getId()).get().getHeadshot();
        message.setHeadshot(myUrl);
        messageRepository.save(message);
        return "redirect:/";
    }

    @PostMapping("/addpic")
    //CHANGE THE PICTURES
    public String processPicForm(@ModelAttribute Message message, @RequestParam("file") MultipartFile file, BindingResult result) {
        String title = messageRepository.findById(message.getId()).get().getTitle();
        String author = messageRepository.findById(message.getId()).get().getSentby();
        String date = messageRepository.findById(message.getId()).get().getDate();
        String content = messageRepository.findById(message.getId()).get().getContent();

        if (file.isEmpty()) {
            return "redirect:/update/pic/" + messageRepository.findById(message.getId()).get().getId();
        }

        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            message.setHeadshot(uploadResult.get("url").toString());
            message.setTitle(title);
            message.setContent(content);
            message.setDate(date);
            message.setSentby(author);
            messageRepository.save(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/update/pic/" + messageRepository.findById(message.getId()).get().getId();
        }
        return "redirect:/";
    }


    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", this.messageRepository.findById(id).get());
        return "messageform";
    }

    @RequestMapping("/update/pic/{id}")
    public String updatePicOfMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", this.messageRepository.findById(id).get());
        return "messagepicform";
    }


    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id) {
        this.messageRepository.deleteById(id);
        return "redirect:/";
    }
}