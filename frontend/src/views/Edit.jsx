import React from "react";
import Navbar from "../components/navbar";
import { useLoaderData } from "react-router-dom";

export default function Edit(props) {
  const data = useLoaderData();

  const onSave = () => {
    //TODO call update from axios
    console.log("Button click -> Save");
  };

  return (
    <div className="edit">
      <Navbar />
      <div className="header-container mt-3 mx-5 justify-content-between">
        <h1>Edit Media</h1>
        <button
          className="btn btn-primary col-2"
          type="button"
          onClick={() => onSave()}
        >
          <i className="bi bi-floppy me-2"></i>
          Save
        </button>
      </div>
      <div className="mt-3 mx-5">
        <p>{data.id}</p>
        <p>{data.name}</p>
        <p>{data.tags}</p>
        <p>{data.media}</p>
        <p>{data.data}</p>
      </div>
    </div>
  );
}
