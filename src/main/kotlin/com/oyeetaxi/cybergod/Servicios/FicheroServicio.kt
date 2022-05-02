package com.oyeetaxi.cybergod.Servicios

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


@Service
class FicheroServicio( @Value("\${file.storage.location:temp}") fileStorageLocation :String) {


    final var fileStoragePath : Path
    final var fileStorageLocation :String

    //TODO Al Inicializar el Contructor Crea el Directorio
    init {

        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize()
        this.fileStorageLocation = fileStorageLocation

        try {
            Files.createDirectories(fileStoragePath)
        } catch (e:IOException) {
            e.printStackTrace()
            throw RuntimeException("Error al Crear el Directorio $fileStoragePath")

        }

    }



    //TODO Metodo para Guardar el Fichero en el Directorio
    fun guardarFichero(file: MultipartFile,fileName: String): String {


        //Nombre por defecto del fichero
        //var fileName = StringUtils.cleanPath(file.originalFilename!!)

//        val char :Char = '"'
//        var fileName = setFileName.replace(char.toString(),"")
//
//        if (fileName.isEmpty()) {
//            fileName = "NoNameFileUploaded.jpg"
//        }

        val filePath = Paths.get(   "$fileStoragePath\\$fileName"     )
                                //fileStoragePath +  "\\" + fileName

        try {
            Files.copy(file.inputStream,filePath,StandardCopyOption.REPLACE_EXISTING)
        } catch (e:IOException) {
            e.printStackTrace()
            throw RuntimeException("Error al Crear el Fichero $fileName")
        }


        return fileName


    }


    fun guardarFicheros(file: MultipartFile): String {


        //Nombre por defecto del fichero
        var fileName = StringUtils.cleanPath(file.originalFilename!!)



        if (fileName.isEmpty()) {
            fileName = "NoNameFileUploaded.jpg"
        }

        val filePath = Paths.get(   "$fileStoragePath\\$fileName"     )
        //fileStoragePath +  "\\" + fileName

        try {
            Files.copy(file.inputStream,filePath,StandardCopyOption.REPLACE_EXISTING)
        } catch (e:IOException) {
            e.printStackTrace()
            throw RuntimeException("Error al Crear el Fichero $fileName")
        }


        return fileName


    }



    fun descargarFichero(fileName: String) : Resource {

        val path : Path =  Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName)
        val resource: Resource


        try {
            resource = UrlResource(path.toUri())

        } catch (e:IOException) {
            e.printStackTrace()
            throw RuntimeException("Error al Leer el Fichero $fileName")
        }

        if (resource.exists() && resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("El Fichero $fileName No exite o es ilegible")
        }



    }


}