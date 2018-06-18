package com.example.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Package com.example.controller
 * Module
 * Project sb-activiti
 * Email 253498229@qq.com
 * Created on 2018/6/17 上午3:43
 *
 * @author wangbin
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Resource
  private IdentityService identityService;

  @PostMapping
  public ResponseEntity insertUser(@RequestBody Map map) {
    User user = identityService.newUser((String) map.get("id"));
    saveUser(map, user);
    return ok(true);
  }

  @PutMapping
  public ResponseEntity updateUser(@RequestBody Map map) {
    User user = identityService.createUserQuery().userId((String) map.get("id")).singleResult();
    saveUser(map, user);
    return ok(true);
  }

  @GetMapping("/{userId}")
  public ResponseEntity getUserGroup(@PathVariable("userId") String userId) {
    List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
    List<Map<String, Object>> result = new ArrayList<>();
    for (Group group : groups) {
      Map<String, Object> map = new HashMap<>();
      map.put("groupId", group.getId());
      map.put("groupName", group.getName());
      result.add(map);
    }
    return ok(result);
  }

  private void saveUser(@RequestBody Map map, User user) {
    user.setFirstName((String) map.get("firstName"));
    user.setLastName((String) map.get("lastName"));
    user.setEmail((String) map.get("email"));
    user.setPassword((String) map.get("password"));
    identityService.saveUser(user);
  }
}
