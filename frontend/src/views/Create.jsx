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

  const [alert, setAlert] = useState({ isVisible: false, msg: "" });
  const [name, setName] = useState("");
  const [tags, setTags] = useState([]);
  const [file, setFile] = useState(null);

  async function onCreate() {
    if (name === "" || tags.length === 0 || !file) {
      setAlert({
        ...alert,
        isVisible: true,
        msg: "Please fill out every form element!",
      });
      return;
    } else {
      setAlert({ ...alert, isVisible: false });
      const formData = new FormData();

      // Update the formData object
      formData.append("name", name);
      formData.append("name", name);
      formData.append("file", file);
      formData.append("date", new Date().toString());
      formData.append("date", new Date().toString());
      formData.append(
        "tags",
        tags.map((item) => parseInt(item.value, 10))
      );

      if (await addMedia(formData)) {
        console.log("Upload Done!");
        console.log("Upload Done!");
        navigate("/");
      } else {
        //TODO: Make warning
        console.log("Upload Failed!");
        setAlert({
          ...alert,
          isVisible: true,
          msg: "Upload has failed, please try again!",
        });
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

      {alert.isVisible && (
        <div
          id="formIncompleteAlert"
          className="alert alert-danger mx-5"
          role="alert"
        >
          {alert.msg}
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
              setAlert({ ...alert, isVisible: false });
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
              setAlert({ ...alert, isVisible: false });
            }}
            value={tags}
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
              setAlert({ ...alert, isVisible: false });
            }}
            required
          />
        </div>
      </div>
    </div>
  );
}
