package com.example.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Package com.example.controller
 * Module
 * Project sb-activiti
 * Email 253498229@qq.com
 * Created on 2018/6/17 上午3:45
 *
 * @author wangbin
 */
@RestController
@RequestMapping("/group")
public class GroupController {
  @Resource
  private IdentityService identityService;

  @PostMapping
  public ResponseEntity insertGroup(@RequestBody Map map) {
    Group group = identityService.newGroup((String) map.get("id"));
    saveGroup(group, map);
    return ok(true);
  }

  @PutMapping
  public ResponseEntity updateGroup(@RequestBody Map map) {
    Group group = identityService.createGroupQuery().groupId((String) map.get("id")).singleResult();
    saveGroup(group, map);
    return ok(true);
  }

  @PostMapping("/{userId}/{groupId}")
  public ResponseEntity createMembership(@PathVariable("userId") String userId, @PathVariable("groupId") String groupId) {
    identityService.createMembership(userId, groupId);
    return ok(true);
  }

  @DeleteMapping("/{userId}/{groupId}")
  public ResponseEntity deleteMembership(@PathVariable("userId") String userId, @PathVariable("groupId") String groupId) {
    identityService.deleteMembership(userId, groupId);
    return ok(true);
  }

  private void saveGroup(Group group, Map map) {
    group.setName((String) map.get("name"));
    identityService.saveGroup(group);
  }
}
