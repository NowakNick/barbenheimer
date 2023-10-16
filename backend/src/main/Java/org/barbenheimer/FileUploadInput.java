package org.barbenheimer;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

public class FileUploadInput {

    @RestForm("name")
    public String name;

    @RestForm("media")
    public FileUpload media;

    @RestForm("date")
    public String date;

    @RestForm("tags")
    public List<Integer> tags;

}
