package com.konovalov.myWebApp.service;


import com.konovalov.myWebApp.domain.User;
import com.konovalov.myWebApp.domain.UserRole;
import com.konovalov.myWebApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
@Autowired
    private UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    //метод возрашение пользователя по имени
    @Override
    public UserDetails loadUserByUsername(String username   ) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user== null){
            throw new UsernameNotFoundException ("User not found");
        }
        return user;

    }
    // добавление пользователя
    public boolean addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        // если в базе уже есть такое имя или введенное поле имени пусто то возращаем фолсе
        if (userFromDb !=null || user.getUsername().isEmpty()){
            return false;
        }
        // если нет сохраняем в базу
        user.setActive(true);
        user.setRoles(Collections.singleton(UserRole.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }
}
