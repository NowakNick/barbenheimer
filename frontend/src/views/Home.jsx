import React from "react";
import Navbar from "../components/navbar";
import MediaList from "../components/mediaList";
import { NavLink, useLoaderData } from "react-router-dom";

export default function Home() {
  const mediaData = useLoaderData();

  const onSearch = () => {
    //TODO filter data
    // if input empty -> show all, else filter
    console.log("Button click -> Search");
  };

  return (
    <div className="home">
      <Navbar />
      <div className="header-container mt-3 mx-5 justify-content-between">
        <h1>Media Storage</h1>
        <NavLink className="btn btn-primary col-2" type="button" to="create">
          <i className="bi bi-plus"></i>
          Upload
        </NavLink>
      </div>
      <div className="row justify-content-start mt-3 mx-3">
        <div className="d-flex col-12 col-md-6 col-lg-4 col-xl-4 col-xxl-3">
          <input
            className="form-control me-2"
            type="search"
            placeholder="Search"
            aria-label="Search"
            onKeyPress={(event) => event.key === "Enter" && onSearch()}
          />
          <button
            onClick={() => onSearch()}
            className="btn btn-outline-success"
            type="button"
          >
            Search
          </button>
        </div>
      </div>
      <MediaList data={mediaData} />
    </div>
  );
}
