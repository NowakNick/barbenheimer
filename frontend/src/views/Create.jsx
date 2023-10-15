import React, { useState } from "react";
import Navbar from "../components/navbar";
import Select from "react-select";
import { addMedia } from "../axios";
import { useNavigate } from "react-router-dom";

export default function Create() {
  const navigate = useNavigate();

  const tagOptions = [
    { value: "1", label: "Hobby" },
    { value: "2", label: "Work" },
    { value: "3", label: "Family" },
    { value: "4", label: "Private" },
    { value: "5", label: "Others" },
  ];

  const typeOptions = [
    { value: "1", label: "Image" },
    { value: "2", label: "Video" },
    { value: "3", label: "Text" },
    { value: "4", label: "Others" },
  ];

  const [isAlertVisible, setAlertVisible] = useState(false);
  const [name, setName] = useState("");
  const [tags, setTags] = useState([]);
  const [type, setType] = useState(0);
  const [file, setFile] = useState(null);

  async function onCreate() {
    if (name === "" || tags.length === 0 || type === 0 || !file) {
      setAlertVisible(true);
      return;
    } else {
      setAlertVisible(false);
      const formData = new FormData();

      // Update the formData object
      formData.append("file", file);
      formData.append("name", name);
      formData.append(
        "tags",
        tags.map((item) => parseInt(item.value, 10))
      );
      formData.append("media", parseInt(type.value, 10));

      console.log(formData);
      if (await addMedia(formData)) {
        navigate("/");
      } else {
        //TODO: Make warning
        console.log("Upload Failed!");
      }
    }
  }

  return (
    <div className="create">
      <Navbar />
      <div className="header-container my-3 mx-5 justify-content-between">
        <h1>Upload new Media</h1>
        <button
          className="btn btn-primary col-2"
          type="button"
          onClick={onCreate}
        >
          Upload
        </button>
      </div>

      {isAlertVisible && (
        <div
          id="formIncompleteAlert"
          className="alert alert-danger mx-5"
          role="alert"
        >
          Please fill out every form element!
        </div>
      )}

      <div className="row mt-0 mx-5 px-2 pb-5 g-4 justify-content-center border border-primary rounded">
        <div className="col-12 col-md-8 col-xl-6">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
            required
          />
        </div>

        <div className="col-12 col-md-8 col-xl-6">
          <label htmlFor="tags">Tags (Multiselect)</label>
          <Select
            id="tags"
            name="tags"
            options={tagOptions}
            isMulti
            onChange={(e) => {
              setTags(e);
            }}
            value={tags}
          />
        </div>

        <div className="col-12 col-md-8 col-xl-6">
          <label htmlFor="Media Type">Media Type</label>
          <Select
            id="mediaType"
            name="mediaType"
            options={typeOptions}
            onChange={(e) => {
              setType(e);
            }}
            required
            value={type}
          />
        </div>

        <div className="col-12 col-md-8 col-xl-6">
          <label htmlFor="mediaFile">Media File</label>
          <input
            className="form-control"
            type="file"
            id="mediaFile"
            name="mediaFile"
            onChange={(e) => {
              setFile(e.target.files[0]);
            }}
            required
          />
        </div>
      </div>
    </div>
  );
}
