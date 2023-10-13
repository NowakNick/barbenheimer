package org.barbenheimer;

import jakarta.ws.rs.FormParam;

import java.io.File;
import java.util.List;

public class FileUploadInput {

    @FormParam("name")
    public String name;

    @FormParam("file")
    public File file;

    @FormParam("date")
    public String date;

    @FormParam("tags")
    public List<String> tags;
}
