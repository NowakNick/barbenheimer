package org.barbenheimer;

import org.jboss.resteasy.reactive.RestForm;

import java.io.File;
import java.util.List;

public class FileUploadInput {

    @RestForm("name")
    public String name;

    @RestForm("file")
    public File file;

    @RestForm("date")
    public String date;

    @RestForm("tags")
    public List<String> tags;
}
