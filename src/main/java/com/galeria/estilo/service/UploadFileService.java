//package com.galeria.estilo.service;
//
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class UploadFileService {
//
//    private String folder="images//";
//
//    public String saveImage(MultipartFile file) throws IOException {
//        if(!file.isEmpty()) {
//            byte [] bytes=file.getBytes();
//            Path path= Paths.get(folder+file.getOriginalFilename());
//            Files.write(path,bytes);
//            return file.getOriginalFilename();
//
//        }
//        return "default.jpg";
//
//    }
//    public void deleteImage(String nombre) {
//        String ruta="images//";
//        File file= new File(ruta+nombre);
//        file.delete();
//    }
//
//}
