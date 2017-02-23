package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.DomainRole;
import java.util.ArrayList;
import java.util.List;

/**
 * UserRoles
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-02-23T15:14:07.329Z")

public class UserRoles   {
  @JsonProperty("isAdministrator")
  private Boolean isAdministrator = null;

  @JsonProperty("roles")
  private List<DomainRole> roles = new ArrayList<DomainRole>();

  public UserRoles isAdministrator(Boolean isAdministrator) {
    this.isAdministrator = isAdministrator;
    return this;
  }

   /**
   * Get isAdministrator
   * @return isAdministrator
  **/
  @ApiModelProperty(value = "")
  public Boolean getIsAdministrator() {
    return isAdministrator;
  }

  public void setIsAdministrator(Boolean isAdministrator) {
    this.isAdministrator = isAdministrator;
  }

  public UserRoles roles(List<DomainRole> roles) {
    this.roles = roles;
    return this;
  }

  public UserRoles addRolesItem(DomainRole rolesItem) {
    this.roles.add(rolesItem);
    return this;
  }

   /**
   * Get roles
   * @return roles
  **/
  @ApiModelProperty(value = "")
  public List<DomainRole> getRoles() {
    return roles;
  }

  public void setRoles(List<DomainRole> roles) {
    this.roles = roles;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRoles userRoles = (UserRoles) o;
    return Objects.equals(this.isAdministrator, userRoles.isAdministrator) &&
        Objects.equals(this.roles, userRoles.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isAdministrator, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserRoles {\n");
    
    sb.append("    isAdministrator: ").append(toIndentedString(isAdministrator)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

