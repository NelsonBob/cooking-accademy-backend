package com.esgi.pa.domain.services.util;

import com.esgi.pa.domain.enums.RoleEnum;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public interface UtilService {
  static final String UPLOAD_DIR = "src/main/resources/files/";

  static final List<RoleEnum> ROLES = Arrays.asList(
    RoleEnum.Admin,
    RoleEnum.Gestionnaire,
    RoleEnum.Client,
    RoleEnum.Livreur,
    RoleEnum.Formateur,
    RoleEnum.Chefs
  );

  static boolean isGranted(RoleEnum role, List<RoleEnum> array) {
    return array.contains(role);
  }

  static boolean isGranted(RoleEnum role) {
    return ROLES.contains(role);
  }

  static String saveFile(MultipartFile file, String fileName)
    throws IOException {
    String filePath = UPLOAD_DIR + fileName;
    byte[] fileBytes = file.getBytes();
    FileCopyUtils.copy(fileBytes, new File(filePath));
    return fileName;
  }
}
