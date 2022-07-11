package com.oyeetaxi.cybergod.futures.fichero.services


import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.utils.Constants.DOWNLOAD_FOLDER
import com.oyeetaxi.cybergod.utils.Constants.FILES_FOLDER
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.text.DecimalFormat





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


    @Throws(NotFoundException::class)
    fun getFileSize(fileName: String?):String {

        val localFileName:String = fileName?.replace("${FILES_FOLDER}/${DOWNLOAD_FOLDER}"  , fileStoragePath.toString())?.replace("/","\\").orEmpty()


        val file = File(localFileName)

        return if (file.exists()) {

            val size: Long = file.length()
            val df = DecimalFormat("0.00")

            val sizeKb = 1024.0f
            val sizeMb = sizeKb * sizeKb
            val sizeGb = sizeMb * sizeKb
            val sizeTerra = sizeGb * sizeKb


            if (size < sizeMb) {
                df.format(size / sizeKb) + " KB"}
            else {
                if (size < sizeGb) {
                    df.format(size / sizeMb)+ " MB"
                } else {
                    if (size < sizeTerra) {
                        df.format(size / sizeGb) + " GB"
                    }  else {"BIG"}
                }

            }.also {
                println("$localFileName -> $it")
            }



//          //FUNCIONA OK
//            val bytes: Long = file.length()
//            val kilobytes = bytes / 1024
//            val megabytes = kilobytes / 1024
//            val gigabytes = megabytes / 1024
//            val terabytes = gigabytes / 1024
//
////            println(String.format("%,d bytes", bytes))
////            println(String.format("%,d KB", kilobytes))
////            println(String.format("%,d MB", megabytes))
////            println(String.format("%,d GB", gigabytes))
////            println(String.format("%,d TB", terabytes))
//
//            String.format("%,d MB", megabytes).also {
//                println("$localFileName -> $it")
//            }


        } else {
            throw NotFoundException("$localFileName no existe!").also {
                println(it.message)
            }
        }
    }


}