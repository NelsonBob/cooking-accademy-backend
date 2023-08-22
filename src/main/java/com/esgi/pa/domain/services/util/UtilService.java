package com.esgi.pa.domain.services.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.esgi.pa.api.dtos.requests.salle.GallerieRequest;
import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UtilService {

  private static final String UPLOAD_DIR = "src/main/resources/files/";

  public static final List<RoleEnum> ROLES = Arrays.asList(
    RoleEnum.Admin,
    RoleEnum.Gestionnaire,
    RoleEnum.Client,
    RoleEnum.Livreur,
    RoleEnum.Formateur,
    RoleEnum.Chefs
  );

  public static boolean isGranted(RoleEnum role, List<RoleEnum> array) {
    return array.contains(role);
  }

  public static boolean isGranted(RoleEnum role) {
    return ROLES.contains(role);
  }

  public String saveFile(MultipartFile file, String fileName)
    throws IOException {
    String filePath = UPLOAD_DIR + fileName;
    byte[] fileBytes = file.getBytes();
    FileCopyUtils.copy(fileBytes, new File(filePath));
    return fileName;
  }
}
