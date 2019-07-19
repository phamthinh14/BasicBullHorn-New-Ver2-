package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void run(String... args) throws Exception {
//        Image image1 = new Image("https://res.cloudinary.com/dzk3cvgwt/image/upload/v1562680320/images_ohigph.jpg");
//        Image image2 = new Image("https://res.cloudinary.com/dzk3cvgwt/image/upload/v1558123225/y2ssmhy33plofmmngzxc.jpg");
//        Image image3 = new Image("https://res.cloudinary.com/dzk3cvgwt/image/upload/v1558114518/udmwnkinb36d18yzyyb2.jpg");


        Message message = new Message("The Samoyed is a breed of large herding dog that descended from the Nenets herding laika, a spitz-type dog, with a thick, white, double-layer coat." +
                "It takes its name from the Samoyedic peoples of Siberia.",
                "05-06-2016", "Arnolad", "Samoyed Characteristics", "https://res.cloudinary.com/dzk3cvgwt/image/upload/v1558108435/Samoyed-1-e1534278656230_thm6aq.jpg");
        messageRepository.save(message);

        message = new Message("Once the mischievous companion of Chinese emperors, and later the mascot of Holland’s royal House of Orange, the small but solid",
                "02-07-2000", "Hasmin", "About Pug", "https://res.cloudinary.com/dzk3cvgwt/image/upload/v1558107590/uwxrc0aac1rd6qpr6ap6.jpg");

        messageRepository.save(message);

        message = new Message("The one-of-a-kind French Bulldog, with his large bat ears and even disposition, is one of the world’s most popular small-dog breeds, especially among city dwellers",
                "07-16-2018", "Mints", "Every one should adopt a French Bulldog", "https://res.cloudinary.com/dzk3cvgwt/image/upload/v1558108688/6164ose4TgL._SX425__suvduz.jpg");

        messageRepository.save(message);

    }

}
