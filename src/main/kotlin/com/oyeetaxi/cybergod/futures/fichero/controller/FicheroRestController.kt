package com.oyeetaxi.cybergod.futures.fichero.controller

import com.oyeetaxi.cybergod.futures.fichero.services.FicheroServicio
import com.oyeetaxi.cybergod.futures.fichero.models.response.FileUploadesResponse
import com.oyeetaxi.cybergod.utils.Constants.DOWNLOAD_FOLDER
import com.oyeetaxi.cybergod.utils.Constants.FILES_FOLDER
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_FICHEROS
import com.oyeetaxi.cybergod.utils.Utils.getFileNameByType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(URL_BASE_FICHEROS)
class FicheroRestController {

    @Autowired
    val ficheroServicio : FicheroServicio? = null


    //EndPoint Para Subir un Solo Fichero
    @PostMapping("/uploadSingleFileByType")
    fun uploadSingleFileByType(@RequestParam file: MultipartFile, @RequestParam("id") id: String, @RequestParam("fileType") fileType: String ): FileUploadesResponse {

        //Nombre del Fichero
        val nombreFichero: String = ficheroServicio!!.guardarFichero(
            file = file,
            fileName = getFileNameByType(id,fileType)
        )

        //Url Destino del Fichero Ej: http://IP:PUERTO/ficheros/download/nombreFichero.extencion
        val url = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("$URL_BASE_FICHEROS/$DOWNLOAD_FOLDER/")
            .path(nombreFichero)
            .toUriString()


        //Url Relativa del Fichero Ej: ficheros/download/nombreFichero.extencion
        val urlRelativa = "$FILES_FOLDER/$DOWNLOAD_FOLDER/$nombreFichero"

        //Tipo
        val contentType: String = file.contentType.toString()

        //Respuesta
        return FileUploadesResponse(fileName = nombreFichero, contentType = contentType, url = urlRelativa)
    }

    //EndPoint Para Subir un Solo Fichero
    //TODO la Llamada al EndPont Seria http://localhost:8080/ficheros/uploadSingleFile
    @PostMapping("/uploadSingleFile")
    fun uploadSingleFile(@RequestParam("file") file: MultipartFile, @RequestParam("fileName") fileName: String): FileUploadesResponse {

        val char :Char = '"'
        val fileNameOK = fileName.replace(char.toString(),"")

         println("fileName is  $fileNameOK" )

        //Nombre del Fichero
        val nombreFichero: String = ficheroServicio!!.guardarFichero(file,fileNameOK)

        //Url Destino del Fichero Ej: http://IP:PUERTO/ficheros/download/nombreFichero.extencion
        val url = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("$URL_BASE_FICHEROS/$DOWNLOAD_FOLDER/")
            .path(nombreFichero)
            .toUriString()

        val urlRelativa = "$FILES_FOLDER/$DOWNLOAD_FOLDER/$nombreFichero"

        println("urlRelativa is  $urlRelativa" )

        //Tipo
        val contentType: String = file.contentType.toString()

        //Respuesta
        return FileUploadesResponse(fileName = nombreFichero, contentType = contentType, url = urlRelativa)
    }


    //TODO EndPoint Para Descargar un Solo Fichero
    @GetMapping("/$DOWNLOAD_FOLDER/{fileName}")
    fun downloadSingleFile(@PathVariable fileName : String, request : HttpServletRequest): ResponseEntity<Resource>{

        val resource: Resource =  ficheroServicio!!.descargarFichero(fileName)
        var response : ResponseEntity<Resource> = ResponseEntity.ok().body(null)


        if (fileName.toString().contains(".apk",true))  {

            response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.filename!!) //Permite Descargar el Fichero
                .body(resource)



        } else {

            var mimeType: String

            try {
                mimeType = request.servletContext.getMimeType(resource.file.absolutePath)
            } catch (e :IOException){
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE
                e.printStackTrace()
            }


            val contentType = MediaType.parseMediaType(mimeType)

            response = ResponseEntity.ok()
                .contentType(contentType)
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.filename!!) //Permite Descargar el Fichero
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.filename!!) //Permite Renderisar el fichero en la Web
                .body(resource)


        }


        return response

    }


//    //TODO EndPoint Para Descargar un Solo Fichero
//    @GetMapping("/$UPDATE_FOLDER/{fileName}")
//    fun downloadSingleFileNoImage(@PathVariable fileName : String, request : HttpServletRequest): ResponseEntity<Resource>{
//
//        val resource: Resource =  ficheroServicio!!.descargarFichero(fileName)
//
//        return ResponseEntity.ok()
//            //.contentType(contentType)
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.filename!!) //Permite Descargar el Fichero
//            //.header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.filename!!) //Permite Renderisar el fichero en la Web
//            .body(resource)
//
//
//    }




    //TODO EndPoint Para Subir Multiples Ficheros
    @PostMapping("/uploadMultipleFile")
    fun uploadMultipleFile(@RequestParam("files") files:   Array<MultipartFile>  ) : MutableList<FileUploadesResponse>  {

        //Verificar el la cantidad de Ficheros subidos al mismo tiempo
        if (files.size > 5) {
            throw RuntimeException("Excedido el Limite de ficheros subidos al mismo tiempo ${files.size} > 5")
        }

        //Verificar el Tamaño de los Ficheros desde application.yml


        val listResponse : MutableList<FileUploadesResponse> = mutableListOf()

        files.asList().forEach { file ->

            //Nombre del Fichero
            val nombreFichero : String = ficheroServicio!!.guardarFicheros(file)

            //Url Destino del Fichero Ej: http://IP:PUERTO/files/nombreFichero
            val url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("$URL_BASE_FICHEROS/$DOWNLOAD_FOLDER/")
                .path(nombreFichero)
                .toUriString()

            val urlRelativa = "$FILES_FOLDER/$DOWNLOAD_FOLDER/$nombreFichero"

            //Tipo
            val contentType: String = file.contentType.toString()

            //Respuesta
            val response = FileUploadesResponse(fileName = nombreFichero, contentType = contentType,url = urlRelativa)
            listResponse.add(response)

        }



        return  listResponse

    }


    //TODO EndPoint Para Descargar Multiples Ficheros en formato zip
    //TODO La Llamada al EndPoint Seria = Ej: http://localhost:8080/ficheros/download/zipDownload?fileName=Fichero1.PNG&fileName=Fichero2.PNG
    @GetMapping("/$DOWNLOAD_FOLDER/zipDownload")
    fun zipDownload(@RequestParam("fileName")  files : Array<String>, response : HttpServletResponse  )  {

        val zos = ZipOutputStream(response.outputStream)

        files.asList().forEach { file ->

            try {
                val resource: Resource = ficheroServicio!!.descargarFichero(file)
                val zipEntry = ZipEntry(resource.filename!!)

                zipEntry.size = resource.contentLength()
                zos.putNextEntry(zipEntry)
                StreamUtils.copy(resource.inputStream,zos)
                zos.closeEntry()
            } catch (e: IndexOutOfBoundsException) {
                throw RuntimeException("Ocurrio un error al preparar el archivo Zip para la descarga")
            }


        }


        zos.finish()
        zos.close()


        response.status = 200
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipFileDownload")


    }


}