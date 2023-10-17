import React, { useState } from "react";
//import { NavLink } from "react-router-dom";
import { deleteMedia } from "../axios";
import { useNavigate } from "react-router-dom";
import { tagOptions } from "../views/Create";

export default function Media(props) {
  const navigate = useNavigate();
  const [alert, setAlert] = useState(false);
  const [imageName] = useState(() => {
    if (props.data.contentType.includes("image")) {
      return "image-placeholder.jpg";
    } else if (props.data.contentType.includes("video")) {
      return "video-placeholder.jpg";
    } else if (props.data.contentType.includes("text")) {
      return "text-placeholder.png";
    } else {
      return "others-placeholder.jpg";
    }
  });

  const onDelete = async () => {
    await deleteMedia(props.data.id)
      .then((res) => {
        if ((res.status = 200)) {
          setAlert(false);
          navigate("/");
        } else {
          setAlert(true);
          console.log("Delete Failed!");
        }
      })
      .catch((err) => {
        console.log(err.msg);
        return false;
      });
  };

  function onDownload() {
    // Decode the base64 data to a Uint8Array
    const binaryString = window.atob(props.data.media);
    const bytes = new Uint8Array(binaryString.length);

    for (let i = 0; i < binaryString.length; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }

    // Create a Blob from the Uint8Array
    const blob = new Blob([bytes], { type: props.data.contentType });

    // Create a URL for the Blob
    const url = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = props.data.mediaName;

    // Trigger a click on the anchor element to start the download
    a.click();

    // Remove resource afterwards
    URL.revokeObjectURL(url);
  }
  const getTag = (item) => {
    const selectedOption = tagOptions.find(
      (option) => option.value === "" + item
    );

    if (selectedOption) {
      return selectedOption.label;
    } else {
      return "Tags undefinded";
    }
  };

  const generateTags = (tags) => {
    let labels = "";
    tags.forEach((item) => {
      if (labels !== "") {
        labels = labels + ", ";
      }
      labels = labels + getTag(item);
    });
    return labels;
  };

  return (
    <div className="media-item col-12 col-md-6 col-lg-4 col-xl-4 col-xxl-3">
      <div className="card">
        <img
          src={"/assets/" + imageName}
          className="card-img-top custom-image img-thumbnail"
          alt={props.data.mediaName}
        />
        <div className="card-body">
          <h5 className="card-title">{props.data.name}</h5>
          <p className="card-text">Tags: {generateTags(props.data.tags)}</p>
          <div className="d-grid gap-2 d-flex justify-content-between">
            {/* <NavLink
              className="btn btn-primary px-3"
              type="button"
              to={"/edit/" + props.data.id}
            >
              Edit
            </NavLink> */}
            <button
              onClick={onDownload}
              className="btn btn-secondary px-3"
              type="button"
            >
              <i className="bi bi-download"></i>
            </button>
            <button
              onClick={onDelete}
              className="btn btn-danger px-3"
              type="button"
            >
              <i className="bi bi-trash"></i>
            </button>
          </div>
          {alert && (
            <div
              id="formIncompleteAlert"
              className="alert alert-danger mt-3"
              role="alert"
            >
              Es ist ein Fehler beim Löschen aufgetreten.
              <br></br>
              Bitte versuchen sie es erneut
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
