package com.meiya.got.service;

import com.meiya.got.exception.ImgException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@Service
public interface uploadService  {

    String updateHead(MultipartFile file, long id) throws IOException, ImgException;

    String addHead(MultipartFile file) throws IOException, ImgException;

}
