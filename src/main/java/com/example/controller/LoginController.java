package com.example.controller;

import org.activiti.engine.IdentityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/login")
public class LoginController {
  @Resource
  private IdentityService identityService;

  @GetMapping("/{userId}/{password}")
  public ResponseEntity login(@PathVariable("userId") String userId, @PathVariable("password") String password) {
    boolean b = identityService.checkPassword(userId, password);
    return ok(b);
  }
}