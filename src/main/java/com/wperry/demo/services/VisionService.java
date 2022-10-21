package com.wperry.demo.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Likelihood;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class VisionService {

  private CloudVisionTemplate cloudVisionTemplate;
  private ResourceLoader resourceLoader;

  @SneakyThrows
  public List<Map<String, Likelihood>> getLikeliHoods(String url) {
    if (null != url) {
      val imageResource = this.resourceLoader.getResource(url);
      val response = this.cloudVisionTemplate.analyzeImage(
          imageResource, Feature.Type.FACE_DETECTION);

      val annotations = response.getFaceAnnotationsList();

      return annotations.stream().map(annotation -> {
        val methods = annotation.getClass().getDeclaredMethods();
        val likeliHoods = new HashMap<String, Likelihood>();
        for (Method method : methods) {
          this.buildLikeliHoodMap(annotation, likeliHoods, method);
        }
        return likeliHoods;
      }).collect(Collectors.toList());
    }
    return Collections.EMPTY_LIST;
  }

  private void buildLikeliHoodMap(FaceAnnotation annotation, final HashMap<String, Likelihood> likeliHoods,
      Method method) {
    if (method.getName().endsWith("Likelihood")) {
      try {
        val likeHood = (Likelihood) method.invoke(annotation);
        if (null != likeHood) {
          val name = method.getName().replace("get", "").toLowerCase();
          likeliHoods.put(name, likeHood);
        }
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        log.error(e.getLocalizedMessage(), e);
      }
    }
  }
}
