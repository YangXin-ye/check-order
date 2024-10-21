package com.iweb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

  ADMIN("ADMIN","管理员");

  private String roleCode;

  private String remark;


}
