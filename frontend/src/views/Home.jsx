import React, { useState } from "react";
import Navbar from "../components/navbar";
import MediaList from "../components/mediaList";
import { NavLink, useLoaderData } from "react-router-dom";
import { tagOptions } from "./Create";

export default function Home() {
  const mediaData = useLoaderData();
  const [mediaFiltered, setMediaFiltered] = useState(mediaData);
  const [search, setSearch] = useState("");

  const getTag = (item) => {
    const selectedOption = tagOptions.find(
      (option) => option.value === "" + item
    );

    if (selectedOption) {
      return selectedOption.label;
    } else {
      return "";
    }
  };

  const generateTags = (tags) => {
    let labels = "";
    tags.forEach((item) => {
      labels = labels + getTag(item);
    });
    return labels;
  };

  const onSearch = () => {
    if (search === "") {
      setMediaFiltered(mediaData);
    } else {
      let arr = [];
      mediaData.forEach((item) => {
        let visible = false;
        if (item.name.toLowerCase().includes(search.toLowerCase())) {
          visible = true;
        } else if (
          item.mediaName.toLowerCase().includes(search.toLowerCase())
        ) {
          visible = true;
        } else if (
          generateTags(item.tags).toLowerCase().includes(search.toLowerCase())
        ) {
          visible = true;
        }
        if (visible === true) {
          arr.push(item);
        }
      });
      setMediaFiltered(arr);
    }
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
            value={search}
            onChange={(e) => {
              setSearch(e.target.value);
            }}
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
      <MediaList data={mediaFiltered} />
    </div>
  );
}
