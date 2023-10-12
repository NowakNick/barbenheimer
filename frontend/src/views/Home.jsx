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
      <div className="row justify-content-between mt-3 mx-5">
        <div className="d-flex col-4">
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
        <NavLink className="btn btn-primary col-2" type="button" to="create">
          <i className="bi bi-plus"></i>
          Upload
        </NavLink>
      </div>
      <MediaList data={mediaData} />
    </div>
  );
}
