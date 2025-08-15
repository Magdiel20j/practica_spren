package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CloudinaryService {

    // definir el tamaño de las imagenes en megabytes
    private static  final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    // definir las extensiones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg",".jpeg",".png"};
    // atributo cloudinary
    private final Cloudinary cloudinary;
    // constructor para inyeccion de dependencias de cloudinary

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
   public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
   }

    private void validateImage(MultipartFile file) {
        // verificar si el archivo esta vacio
        if (file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacio");
        }

        // verificar si el tamaño excede eñ limite permitido
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5mb");
        }
        // verificar el nombre original del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null){
            throw  new IllegalArgumentException("Nombre de archivo invalido");
        }


        // extraer y validar la extension del archivo
        String extenseion = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extenseion)){
            throw  new IllegalArgumentException("solo se permite archivos jpg, jpeg, png");
        }

        // verificar que el tipo mime ea una imagen
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("el archivo debe ser una imagen valida")
        }
    }

}
