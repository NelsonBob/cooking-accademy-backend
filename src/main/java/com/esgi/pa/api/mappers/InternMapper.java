package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface InternMapper {
  static GetInternResponse toGetInternResponse(Users users, Intern intern) {
    return new GetInternResponse(
      users.getId(),
      users.getName(),
      users.getEmail(),
      users.getRole(),
      intern.getFonction()
    );
  }

  static GetInternResponse toGetInternResponse(Intern intern) {
    return new GetInternResponse(
      intern.getUsers().getId(),
      intern.getUsers().getName(),
      intern.getUsers().getEmail(),
      intern.getUsers().getRole(),
      intern.getFonction()
    );
  }

  public static List<GetInternResponse> toGetInternResponse(
    List<Users> entities,
    List<Intern> interns
  ) {
    return entities
      .stream()
      .map(entity ->
        toGetInternResponse(entity, getInternByUser(entity, interns))
      )
      .distinct()
      .collect(Collectors.toList());
  }

  private static Intern getInternByUser(Users users, List<Intern> interns) {
    return interns
      .stream()
      .filter(intern -> intern.getUsers().equals(users))
      .findFirst()
      .orElse(null);
  }
}
