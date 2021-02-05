//package com.docker.demo.services
//
//
//import com.docker.demo.entities.UserPrincipal
//import com.docker.demo.repositories.com.docker.demo.repositories.users.UserRepo
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Service
//import reactor.core.publisher.Mono
//
//@Service
//class UserService implements UserDetailsService {
//
//    com.docker.demo.repositories.users.UserRepo repo
//
//    UserService(com.docker.demo.repositories.users.UserRepo repo) {
//        this.repo = repo
//    }
//
//    @Override
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Mono<User> user = repo.findByUsername(username).s
//
//        new UserPrincipal(user);
//    }
//}
